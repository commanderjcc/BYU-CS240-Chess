package WebSockets.CommandHandlers;

import Models.Game;
import Services.GameService;
import WebSockets.WSSessionsManager;
import chess.ChessGame;
import com.google.gson.Gson;
import webSocketMessages.userCommands.JoinPlayerCommand;
import webSocketMessages.userCommands.UserGameCommand;

import org.eclipse.jetty.websocket.api.Session;
import java.io.IOException;
import java.util.Objects;

/**
 * This class represents a command handler for joining a player to a game.
 * It extends the CommandHandler class and provides the functionality to handle the JoinPlayerCommand.
 */
public class JoinPlayerCommandHandler extends CommandHandler {
    /**
     * Constructs a JoinPlayerCommandHandler with the specified WSSessionsManager and GameService.
     *
     * @param sessionsManager The WSSessionsManager used to manage WebSocket sessions.
     * @param gameService     The GameService used to interact with the game logic.
     */
    public JoinPlayerCommandHandler(WSSessionsManager sessionsManager, GameService gameService) {
        super(sessionsManager, gameService);
    }

    /**
     * Handles the JoinPlayerCommand by validating the player color, game ID, and user's team.
     * Adds the session to the sessions manager and sends appropriate messages to the client.
     *
     * @param session The WebSocket session of the client.
     * @param command The UserGameCommand representing the join player command.
     * @throws IOException         If an I/O error occurs while sending messages to the client.
     */
    public void handle(Session session, UserGameCommand command) throws IOException {
        JoinPlayerCommand joinPlayerCommand = (JoinPlayerCommand) command;

        if (joinPlayerCommand.getPlayerColor() == null) {
            session.getRemote().sendString(new Gson().toJson(new webSocketMessages.serverMessages.ErrorMessage("Error: Player color not specified")));
            return;
        }

        //Make sure GameID is valid
        Game game;
        try {
            game = gameService.getGame(joinPlayerCommand.getGameID());
        } catch (Exception e) {
            session.getRemote().sendString(new Gson().toJson(new webSocketMessages.serverMessages.ErrorMessage("Error: Invalid GameID")));
            return;
        }

        //Make sure the user is joining the right team
        if (joinPlayerCommand.getPlayerColor() == ChessGame.TeamColor.BLACK && !Objects.equals(game.getBlackUsername(), command.getUsername())) {
            session.getRemote().sendString(new Gson().toJson(new webSocketMessages.serverMessages.ErrorMessage("Error: You are not the black player")));
            return;
        }

        if (joinPlayerCommand.getPlayerColor() == ChessGame.TeamColor.WHITE && !Objects.equals(game.getWhiteUsername(), command.getUsername())) {
            session.getRemote().sendString(new Gson().toJson(new webSocketMessages.serverMessages.ErrorMessage("Error: You are not the white player")));
            return;
        }

        sessionsManager.addSession(joinPlayerCommand.getGameID(), joinPlayerCommand.getUsername(), session);

        var loadGameMessage = new webSocketMessages.serverMessages.LoadGameMessage(game.getGame());
        session.getRemote().sendString(gson.toJson(loadGameMessage));

        var message = String.format("Player %s joined the game", joinPlayerCommand.getUsername());
        var notification = new webSocketMessages.serverMessages.NotificationMessage(message);
        sessionsManager.broadcast(joinPlayerCommand.getGameID(), notification, joinPlayerCommand.getUsername());
    }
}
