package WebSockets.CommandHandlers;

import Services.GameService;
import WebSockets.WSSessionsManager;
import dataAccess.DataAccessException;
import webSocketMessages.userCommands.UserGameCommand;

import org.eclipse.jetty.websocket.api.Session;
import java.io.IOException;
import java.util.Objects;

/**
 * The ResignCommandHandler class is responsible for handling the resign command in a chess game.
 * It extends the CommandHandler class and provides the implementation for handling the resign command.
 */
public class ResignCommandHandler extends CommandHandler {
    /**
     * Constructs a ResignCommandHandler with the specified WSSessionsManager and GameService.
     *
     * @param sessionsManager The WSSessionsManager used to manage WebSocket sessions.
     * @param gameService     The GameService used to interact with the game logic.
     */
    public ResignCommandHandler(WSSessionsManager sessionsManager, GameService gameService) {
        super(sessionsManager, gameService);
    }

    /**
     * Handles the resign command by updating the game state and notifying all players in the game.
     *
     * @param session The WebSocket session of the player.
     * @param command The UserGameCommand representing the resign command.
     * @throws IOException         If an I/O error occurs while sending messages to the client.
     * @throws DataAccessException If an error occurs while accessing the game data.
     */
    public void handle(Session session, UserGameCommand command) throws DataAccessException, IOException {
        var resignCommand = (webSocketMessages.userCommands.ResignCommand) command;
        var gameID = resignCommand.getGameID();
        var username = resignCommand.getUsername();
        var game = gameService.getGame(gameID);

        if (!Objects.equals(game.getBlackUsername(), username) && !Objects.equals(game.getWhiteUsername(), username)) {
            session.getRemote().sendString(gson.toJson(new webSocketMessages.serverMessages.ErrorMessage("Error: Player not in game")));
            return;
        }

        if (game.isOver()) {
            session.getRemote().sendString(gson.toJson(new webSocketMessages.serverMessages.ErrorMessage("Error: Game is over")));
            return;
        }

        game.setOver();

        gameService.updateGame(game);

        var message = String.format("Player %s forfeits", username);
        var notification = new webSocketMessages.serverMessages.NotificationMessage(message);
        sessionsManager.broadcast(gameID, notification, "");
    }
}
