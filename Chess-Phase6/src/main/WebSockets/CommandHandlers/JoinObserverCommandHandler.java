package WebSockets.CommandHandlers;

import Models.Game;
import Services.GameService;
import WebSockets.WSSessionsManager;
import com.google.gson.Gson;
import webSocketMessages.userCommands.JoinObserverCommand;
import webSocketMessages.userCommands.UserGameCommand;

import org.eclipse.jetty.websocket.api.Session;
import java.io.IOException;

/**
 * This class represents a command handler for joining a game as an observer.
 * It extends the CommandHandler class and handles the logic for joining a game as an observer.
 */
public class JoinObserverCommandHandler extends CommandHandler {
    /**
     * Constructs a JoinObserverCommandHandler with the specified sessions manager and game service.
     *
     * @param sessionsManager The sessions manager responsible for managing WebSocket sessions.
     * @param gameService     The game service responsible for managing game-related operations.
     */
    public JoinObserverCommandHandler(WSSessionsManager sessionsManager, GameService gameService) {
        super(sessionsManager, gameService);
    }

    /**
     * Handles the join observer command by adding the observer session, sending game data to the observer,
     * and broadcasting a notification to all players in the game.
     *
     * @param session The WebSocket session of the observer.
     * @param command The join observer command.
     * @throws IOException         If an I/O error occurs while sending WebSocket messages.
     */
    public void handle(Session session, UserGameCommand command) throws IOException {
        JoinObserverCommand joinObserverCommand = (JoinObserverCommand) command;

        Game game;
        try {
            game = gameService.getGame(joinObserverCommand.getGameID());
        } catch (Exception e) {
            session.getRemote().sendString(new Gson().toJson(new webSocketMessages.serverMessages.ErrorMessage("Error: Invalid GameID")));
            return;
        }

        sessionsManager.addSession(joinObserverCommand.getGameID(), joinObserverCommand.getUsername(), session);

        var loadGameMessage = new webSocketMessages.serverMessages.LoadGameMessage(game.getGame());
        session.getRemote().sendString(gson.toJson(loadGameMessage));

        var message = String.format("Player %s is now observing", joinObserverCommand.getUsername());
        var notification = new webSocketMessages.serverMessages.NotificationMessage(message);
        sessionsManager.broadcast(joinObserverCommand.getGameID(), notification, joinObserverCommand.getUsername());
    }
}
