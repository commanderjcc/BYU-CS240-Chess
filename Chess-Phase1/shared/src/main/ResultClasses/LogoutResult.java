package ResultClasses;

/**
 * Represents the result of a logout operation.
 * Inherits from the Result class.
 */
public class LogoutResult extends Result {
    /**
     * Constructs a LogoutResult object with null status and message.
     */
    public LogoutResult() {
        super();
    }

    /**
     * Constructs a LogoutResult object with the given status and null message.
     * @param status The status code of the result.
     */
    public LogoutResult(int status) {
        super(status);
    }

    /**
     * Constructs a LogoutResult object with the given status and message.
     * @param status The status code of the result.
     * @param message The message associated with the result.
     */
    public LogoutResult(int status, String message) {
        super(status, message);
    }
}
