package ui.GameplayHandlers;

import chess.ChessGame;
import chessGameImpl.CGame;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ui.ChessBoardPrinter;
import ui.Printer;
import webSocketMessages.serverMessages.ServerMessage;

/**
 * The GameHandler class is responsible for handling game-related operations and interactions.
 * It provides methods for updating the game state, printing messages and errors, and handling incoming messages from the server.
 */
public class GameHandler {

    // Pretty Printers
    /**
     * The Printer object is used to print messages to the console.
     */
    protected final Printer p = new Printer();

    /**
     * The ChessBoardPrinter object is used to print the chess board to the console.
     */
    protected final ChessBoardPrinter cbp = new ChessBoardPrinter();

    /**
     * The ChessGame object is used to store the current game state.
     */
    protected ChessGame game;

    
    /**
     * Returns the team color of the chess game.
     *
     * @return the team color of the chess game
     */
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    /**
     * The team color of the chess game.
     */
    private ChessGame.TeamColor teamColor;

    /**
     * Sets the team color for this chess game
     * @param teamColor the teamColor to set
     */
    public void setTeamColor(ChessGame.TeamColor teamColor) {
        this.teamColor = teamColor;
    }


    /**
     * Updates the current game with a new ChessGame object.
     * 
     * @param game The new ChessGame object to update the current game with.
     */
    public void updateGame(ChessGame game) {
        this.game = game;

        //Update the board
        p.println("");
        cbp.printBoard(game.getBoard(), this.getTeamColor());
        p.reset();
        p.setColor(Printer.Color.GREEN);
        p.print(">>> ");
    }

    /**
     * Prints a message to the console with specific message formatting.
     *
     * @param message the message to be printed
     */
    public void printMessage(String message) {
        p.reset();
        p.setColor(Printer.Color.LIGHT_GREY);
        p.println("");
        p.println("[Server] " + message);
        p.setColor(Printer.Color.GREEN);
        p.print(">>> ");
    }

    /**
     * Prints an error message to the screen.
     * 
     * @param message the error message to be printed
     */
    public void printError(String message) {
        p.reset();
        p.setColor(Printer.Color.RED);
        p.println("");
        p.println(message);
        p.setColor(Printer.Color.GREEN);
        p.print(">>> ");
    }

    /**
     * Handles an incoming websocket message from the server
     * 
     * @param message the message to be handled
     */
    public void handleMessage(String message) {
        var serverMessage = new Gson().fromJson(message, ServerMessage.class);
        switch (serverMessage.getServerMessageType()) {
            case LOAD_GAME -> {
                // Load game
                var game = new GsonBuilder().registerTypeAdapter(ChessGame.class, new CGame.ChessGameTA()).create()
                        .fromJson(message, webSocketMessages.serverMessages.LoadGameMessage.class);
                updateGame(game.getGame());
            }
            case NOTIFICATION -> {
                // Print notification
                var notification = new Gson().fromJson(message, webSocketMessages.serverMessages.NotificationMessage.class);
                printMessage(notification.getMessage());
            }
            case ERROR -> {
                // Print error
                var error = new Gson().fromJson(message, webSocketMessages.serverMessages.ErrorMessage.class);
                printError(error.getErrorMessage());
            }
            default -> {
            }
        }
    }
}
