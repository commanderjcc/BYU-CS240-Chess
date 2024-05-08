package RequestClasses;

import Models.AuthToken;

/**
 * Represents a request to join a game.
 */
public class JoinGameRequest extends Request {

    /**
     * The ID of the game to join.
     */
    private Integer gameID;

    /**
     * The color the player wants to play as.
     */
    private String playerColor;

    /**
     * Creates a new instance of JoinGameRequest with null values for gameID and playerColor.
     */
    public JoinGameRequest() {
        super();
        this.gameID = null;
        this.playerColor = null;
    }

    /**
     * Creates a new instance of JoinGameRequest with the specified authToken, gameID, and playerColor.
     * @param authToken The authToken of the user making the request.
     * @param gameID The ID of the game to join.
     * @param playerColor The color the player wants to play as.
     */
    public JoinGameRequest(AuthToken authToken, Integer gameID, String playerColor) {
        super(authToken);
        this.gameID = gameID;
        this.playerColor = playerColor;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }

    public String getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(String playerColor) {
        this.playerColor = playerColor;
    }
}
