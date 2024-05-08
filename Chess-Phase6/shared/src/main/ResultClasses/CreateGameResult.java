package ResultClasses;

/**
 * Represents the result of a create game request.
 */
public class CreateGameResult extends Result {

    /**
     * The ID of the newly created game.
     */
    private Integer gameID;

    /**
     * Creates a new instance of CreateGameResult with a null game ID.
     */
    public CreateGameResult() {
        super();
        this.gameID = null;
    }

    /**
     * Creates a new instance of a successful CreateGameResult with the specified game ID and a default status of 200.
     * @param gameID The ID of the newly created game.
     */
    public CreateGameResult(Integer gameID) {
        super(200);
        this.gameID = gameID;
    }

    /**
     * Creates a new instance of CreateGameResult with the specified status and message, and a null game ID.
     * @param status The status code of the result.
     * @param message The message associated with the result.
     */
    public CreateGameResult(int status, String message) {
        super(status, message);
        this.gameID = null;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }
}
