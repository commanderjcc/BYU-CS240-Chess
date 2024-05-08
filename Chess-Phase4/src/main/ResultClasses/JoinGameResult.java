package ResultClasses;

/**
 * JoinGameResult class is a subclass of Result class that represents the result of a join game request.
 */
public class JoinGameResult extends Result {
    /**
     * Creates a new instance of JoinGameResult with null values
     */
    public JoinGameResult() {
        super();
    }

    /**
     * Creates a new instance of JoinGameResult with the specified status
     * @param status the status code of the result
     */
    public JoinGameResult(int status) {
        super(status);
    }

    /**
     * Creates a new instance of JoinGameResult with the specified status and message
     * @param status the status code of the result
     * @param message the message associated with the result
     */
    public JoinGameResult(int status, String message) {
        super(status, message);
    }
}
