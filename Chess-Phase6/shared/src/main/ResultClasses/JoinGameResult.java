package ResultClasses;

import chess.ChessGame;

/**
 * JoinGameResult class is a subclass of Result class that represents the result of a join game request.
 */
public class JoinGameResult extends Result {
    /**
     * Gets the player color associated with the join game result.
     * @return the player color
     */
    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }

    /**
     * Sets the player color for the join game result.
     * @param playerColor the player color to set
     */
    public void setPlayerColor(ChessGame.TeamColor playerColor) {
        this.playerColor = playerColor;
    }

    /**
     * Sets the game ID for the join game result.
     * @param gameID the game ID to set
     */
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    private ChessGame.TeamColor playerColor;

    private int gameID;

    /**
     * Gets the game ID associated with the join game result.
     * @return the game ID
     */
    public int getGameID() {
        return gameID;
    }

    /**
     * Creates a new instance of JoinGameResult with null values.
     */
    public JoinGameResult() {
        super();
        this.playerColor = null;
    }

    /**
     * Creates a new instance of JoinGameResult with the specified status, player color, and game ID.
     * @param status the status code of the result
     * @param playerColor the player color associated with the result
     * @param gameID the game ID associated with the result
     */
    public JoinGameResult(int status, ChessGame.TeamColor playerColor, int gameID) {
        super(status);
        this.playerColor = playerColor;
        this.gameID = gameID;
    }

    /**
     * Creates a new instance of JoinGameResult with the specified status and message.
     * @param status the status code of the result
     * @param message the message associated with the result
     */
    public JoinGameResult(int status, String message) {
        super(status, message);
    }
}
