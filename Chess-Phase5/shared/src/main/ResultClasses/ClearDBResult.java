package ResultClasses;

/**
 * Represents the result of a database clearing operation.
 */
public class ClearDBResult extends Result {

    /**
     * Constructs a new ClearDBResult object with default status and message.
     */
    public ClearDBResult() {
        super();
    }

    /**
     * Constructs a new ClearDBResult object with the specified status and message.
     *
     * @param status the status code of the result
     * @param message the message associated with the result
     */
    public ClearDBResult(int status, String message) {
        super(status, message);
    }

    /**
     * Constructs a new ClearDBResult object with the specified status
     *
     * @param status the status code of the result
     */
    public ClearDBResult(int status) {
        super(status);
    }
}
