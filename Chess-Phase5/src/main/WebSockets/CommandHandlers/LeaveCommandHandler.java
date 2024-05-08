package WebSockets.CommandHandlers;

import Services.GameService;
import WebSockets.WSSessionsManager;
import dataAccess.DataAccessException;
import webSocketMessages.userCommands.LeaveCommand;
import webSocketMessages.userCommands.UserGameCommand;

import org.eclipse.jetty.websocket.api.Session;
import java.io.IOException;
import java.util.Objects;

/**
     * This class represents a command handler for the "leave" command in the WebSocket API.
     * It handles the logic for when a player wants to leave a game session.
     */
public class LeaveCommandHandler extends CommandHandler {
    
    /**
     * Constructs a LeaveCommandHandler with the specified WSSessionsManager and GameService.
     * 
     * @param sessionsManager The WSSessionsManager object that manages the WebSocket sessions.
     * @param gameService The GameService object that provides game-related functionality.
     */
    public LeaveCommandHandler(WSSessionsManager sessionsManager, GameService gameService) {
        super(sessionsManager, gameService);
    }

    /**
     * Handles the leave command by removing the session associated with the game and updating the game state accordingly.
     * Notifies other players and observers about the player leaving the game.
     *
     * @param command the user game command containing the leave command details
     * @throws IOException         if an I/O error occurs
     * @throws DataAccessException if there is an error accessing the data
     */
    public void handle(UserGameCommand command) throws IOException, DataAccessException {
        LeaveCommand leaveCommand = (LeaveCommand) command;
        var gameID = leaveCommand.getGameID();
        var username = leaveCommand.getUsername();
        sessionsManager.removeSession(gameID, username);

        var game = gameService.getGame(gameID);
        if (Objects.equals(game.getWhiteUsername(), username)) {
            game.setWhiteUsername(null);
        } else if (Objects.equals(game.getBlackUsername(), username)) {
            game.setBlackUsername(null);
        } else {
            game.getObservers().remove(username);
        }

        gameService.updateGame(game);

        var message = String.format("Player %s left the game", username);
        var notification = new webSocketMessages.serverMessages.NotificationMessage(message);
        sessionsManager.broadcast(gameID, notification, username);
    }
}
