package ui;

import Exceptions.ResponseException;
import Models.AuthToken;
import ServerFacade.ServerFacade;
import WSFacade.WSFacade;
import chess.ChessGame;
import ui.GameplayHandlers.*;

/**
 * The GameplayUI class is responsible for handling the gameplay commands.
 * It extends the GameHandler class and provides methods to join, observe, and play a game.
 */
public class GameplayUI extends GameHandler {
    // AuthToken
    private final AuthToken authToken;

    // Game ID
    private final Integer gameID;

    // Server Facade
    private final ServerFacade serverFacade;

    // Websocket Facade
    private final WSFacade wsFacade;

    // Handlers
    private final GHelpHandler helpHandler;
    private final GHighlightHelper highlightHandler;

    private final GMoveHandler moveHandler;

    private final GJoinHandler joinHandler;
    private final GObserveHandler joinObserverHandler;
    private final GLeaveHandler leaveHandler;
    private final GResignHandler resignHandler;
    private final GRedrawHandler redrawHandler;

    /**
     * Constructs a GameplayUI object with the given AuthToken, URL, team color, and game ID.
     *
     * @param authToken  The AuthToken to use for authentication.
     * @param URL        The URL of the server.
     * @param teamColor  The team color to join as.
     * @param gameID     The ID of the game to join.
     * @throws ResponseException If an error occurs while sending the join request.
     */
    public GameplayUI(AuthToken authToken, String URL, ChessGame.TeamColor teamColor, Integer gameID) throws ResponseException {
        super();
        this.authToken = authToken;
        this.setTeamColor(teamColor);
        this.gameID = gameID;
        this.serverFacade = new ServerFacade(URL);
        this.wsFacade = new WSFacade(URL, this);

        // Set up the local handlers
        this.helpHandler = new GHelpHandler();
        this.highlightHandler = new GHighlightHelper(teamColor);
        this.redrawHandler = new GRedrawHandler(teamColor);

        // Set up the online handlers
        this.moveHandler = new GMoveHandler(wsFacade, authToken);
        this.joinHandler = new GJoinHandler(wsFacade, authToken);
        this.joinObserverHandler = new GObserveHandler(wsFacade, authToken);
        this.leaveHandler = new GLeaveHandler(wsFacade, authToken);
        this.resignHandler = new GResignHandler(wsFacade, authToken);
    }

    /**
     * Sends a join request to the server, initiating the websocket connection. Called right after a Join game command is issued to the webserver.
     */
    public void join() throws ResponseException {
        // Join the game
        joinHandler.joinGame(this.gameID, this.getTeamColor());
    }

    
    /**
     * Executes the read-eval-print loop (REPL) for the gameplay UI.
     * 
     * @param input the user input
     * @return true if the REPL should continue, false otherwise
     */
    public boolean replLoop(String input) {
        try {
            var args = input.split(" ");
            if (args.length == 0) {
                return true;
            }

            switch (args[0]) {
                case "help" -> helpHandler.help();
                case "quit" -> {
                    return false;
                }
                case "move" -> moveHandler.extractMoveAndSend(gameID, args);
                case "view" -> highlightHandler.extractPositionAndView(game, args);

                case "leave" -> {
                    leaveHandler.leave(gameID);
                    return false;
                }
                case "resign" -> resignHandler.resign(gameID);
                case "redraw" -> redrawHandler.redraw(game);
                default -> {
                    p.reset();
                    p.setColor(Printer.Color.RED);
                    p.println("Unknown command: " + input);
                    helpHandler.help();
                }
            }
            return true;
        } catch (Exception e) {
            p.reset();
            p.setColor(Printer.Color.RED);
            p.println("Unexpected Error: " + e.getMessage());
            return true;
        }
    }


    /**
     * Sends a join observer request to the server, initiating the websocket connection. Called right after an Observe game command is issued to the webserver.
     * @throws ResponseException if there is an error in the response.
     */
    public void observe() throws ResponseException {
        // Observe the game
        joinObserverHandler.joinGame(this.gameID);
    }
}
