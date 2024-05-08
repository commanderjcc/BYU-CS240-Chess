package WebSockets;

import Exceptions.ResponseException;
import Models.AuthToken;
import Services.AuthService;
import Services.GameService;
import WebSockets.CommandHandlers.*;
import chess.ChessMove;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.websocket.api.Session;

import dataAccess.DataAccessException;
import org.eclipse.jetty.websocket.api.annotations.*;
import webSocketMessages.userCommands.*;

import java.io.IOException;

/**
 * This class represents the WebSocket server.
 * It handles the WebSocket connections and messages.
 */
@WebSocket
public class WSServer {

    // Sessions Manager
    private final WSSessionsManager sessionsManager = new WSSessionsManager();

    // Auth Checker
    private final AuthService authService;

    // Game update Service
    private final GameService gameService;


    //Handlers
    private final JoinPlayerCommandHandler joinPlayerCommandHandler;
    private final JoinObserverCommandHandler joinObserverCommandHandler;
    private final LeaveCommandHandler leaveCommandHandler;
    private final MoveCommandHandler moveCommandHandler;
    private final ResignCommandHandler resignCommandHandler;

    /**
     * Handles the WebSocket messages.
     *
     * @param session The WebSocket session of the client.
     * @param message The message sent by the client.
     * @throws IOException         If an I/O error occurs while sending messages to the client.
     * @throws DataAccessException If an error occurs while accessing the game data.
     */
    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException, DataAccessException, InvalidMoveException {
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        var tmp = new AuthToken(command.getAuthString(), "");

        //Check AuthToken
        try {
            if (!authService.validateAuthToken(tmp)) {
                session.getRemote().sendString(new Gson().toJson(new webSocketMessages.serverMessages.ErrorMessage("AuthToken Error: Invalid AuthToken")));
                return;
            }
        } catch (Exception e) {
            session.getRemote().sendString(new Gson().toJson(new webSocketMessages.serverMessages.ErrorMessage("AuthToken Error: " + e.getMessage())));
            return;
        }

        var trueAuthToken = new AuthToken(command.getAuthString(), authService.getUsername(tmp));

        switch (command.getCommandType()) {
            case JOIN_PLAYER -> {
                command = new Gson().fromJson(message, JoinPlayerCommand.class);
                command.setAuthToken(trueAuthToken);
                joinPlayerCommandHandler.handle(session, command);
            }
            case JOIN_OBSERVER -> {
                command = new Gson().fromJson(message, JoinObserverCommand.class);
                command.setAuthToken(trueAuthToken);
                joinObserverCommandHandler.handle(session, command);
            }
            case LEAVE -> {
                command = new Gson().fromJson(message, LeaveCommand.class);
                command.setAuthToken(trueAuthToken);
                leaveCommandHandler.handle(command);
            }
            case MAKE_MOVE -> {
                var gson = new GsonBuilder().registerTypeAdapter(ChessMove.class, new ChessMove.ChessMoveTA()).create();
                command = gson.fromJson(message, MoveCommand.class);
                command.setAuthToken(trueAuthToken);
                moveCommandHandler.handle(session, command);
            }
            case RESIGN -> {
                command = new Gson().fromJson(message, ResignCommand.class);
                command.setAuthToken(trueAuthToken);
                resignCommandHandler.handle(session, command);
            }
        }
    }

    //Required methods for WebSocket, only logging for now
    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("Connected to " + session.toString());
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        System.out.println("Closed: " + statusCode + " " + reason);
    }

    @OnWebSocketError
    public void onError(Session session, Throwable error) {
        System.out.println("Error: " + error.getMessage());
    }

    /**
     * Constructs a WSServer with the specified AuthService and GameService.
     *
     * @param authService The AuthService used to validate authentication tokens.
     * @param gameService The GameService used to interact with the game logic.
     */
    public WSServer(AuthService authService, GameService gameService) {
        this.gameService = gameService;
        this.authService = authService;
        this.joinPlayerCommandHandler = new JoinPlayerCommandHandler(sessionsManager, gameService);
        this.joinObserverCommandHandler = new JoinObserverCommandHandler(sessionsManager, gameService);
        this.leaveCommandHandler = new LeaveCommandHandler(sessionsManager, gameService);
        this.moveCommandHandler = new MoveCommandHandler(sessionsManager, gameService);
        this.resignCommandHandler = new ResignCommandHandler(sessionsManager, gameService);
    }
}
