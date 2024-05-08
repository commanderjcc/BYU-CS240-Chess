package WSFacade;

import Exceptions.ResponseException;
import Models.AuthToken;
import chess.ChessGame;
import chess.ChessMove;
import chessGameImpl.CGame;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ui.GameplayHandlers.GameHandler;
import webSocketMessages.userCommands.UserGameCommand;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * The WSFacade class is responsible for handling the WebSocket connection to the server.
 * It extends the Endpoint class and provides methods for sending messages to the server.
 */
public class WSFacade extends Endpoint {
    // Session
    private final Session session;

    // GameHandler - a GameplayUI object
    private final GameHandler gameHandler;

    /**
     * The Gson object is used to serialize and deserialize ChessGame objects.
     */
    private final Gson gson = new GsonBuilder().registerTypeAdapter(ChessGame.class, new CGame.ChessGameTA()).create();

    /**
     * Constructs a WSFacade object with the given URL and GameHandler.
     *
     * @param url         The URL of the server to connect to.
     * @param gameHandler The GameHandler to use for handling incoming messages.
     * @throws ResponseException If an error occurs while connecting to the server.
     */
    public WSFacade(String url, GameHandler gameHandler) throws ResponseException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/connect");
            this.gameHandler = gameHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    gameHandler.handleMessage(message);
                }
            });

        } catch (URISyntaxException | DeploymentException | IOException e) {
            throw new ResponseException(500, e.getMessage());
        }
    }

    /**
     * Sends a message to the server.
     *
     * @param command The UserGameCommand to send to the server.
     * @throws ResponseException If an error occurs while sending the message.
     */
    private void send(UserGameCommand command) throws ResponseException {
        try {
            session.getBasicRemote().sendText(gson.toJson(command));
        } catch (IOException e) {
            throw new ResponseException(500, e.getMessage());
        }
    }

    /**
     * Sends a join player command to the server.
     * 
     * @param authToken The authentication token of the player.
     * @param gameID The ID of the game the player wants to join.
     * @param playerColor The color of the player's team.
     * @throws ResponseException If there is an error in the response from the server.
     */
    public void sendJoinPlayer(AuthToken authToken, Integer gameID, ChessGame.TeamColor playerColor) throws ResponseException {
        var command = new webSocketMessages.userCommands.JoinPlayerCommand(authToken, gameID, playerColor);
        send(command);
    }
    
    /**
     * Sends a join observer command to the server.
     * 
     * @param authToken The authentication token of the user.
     * @param gameID The ID of the game to join as an observer.
     * @throws ResponseException If there is an error in the response from the server.
     */
    public void sendJoinObserver(AuthToken authToken, Integer gameID) throws ResponseException {
        var command = new webSocketMessages.userCommands.JoinObserverCommand(authToken, gameID);
        send(command);
    }

    /**
     * Sends a chess move to the server.
     * 
     * @param authToken The authentication token of the user.
     * @param gameID The ID of the game.
     * @param move The chess move to be sent.
     * @throws ResponseException If there is an error in the response from the server.
     */
    public void sendMove(AuthToken authToken, Integer gameID, ChessMove move) throws ResponseException {
        var command = new webSocketMessages.userCommands.MoveCommand(authToken, gameID, move);
        send(command);
    }

    /**
     * Sends a leave game command to the server.
     * 
     * @param authToken The authentication token of the user.
     * @param gameID The ID of the game to leave.
     * @throws ResponseException If there is an error in the response from the server.
     */
    public void sendLeaveGame(AuthToken authToken, Integer gameID) throws ResponseException {
        var command = new webSocketMessages.userCommands.LeaveCommand(authToken, gameID);
        send(command);
    }

    /**
     * Sends a resignation command to the server for the specified game.
     *
     * @param authToken The authentication token of the user.
     * @param gameID The ID of the game.
     * @throws ResponseException If an error occurs while sending the command.
     */
    public void sendResign(AuthToken authToken, Integer gameID) throws ResponseException {
        var command = new webSocketMessages.userCommands.ResignCommand(authToken, gameID);
        send(command);
    }

    /**
     * Ignored method.
     */
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
}
