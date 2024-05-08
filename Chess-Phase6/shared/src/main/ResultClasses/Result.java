package ResultClasses;

/**
 * This class represents a Result object. It is the superclass of all other Result classes.
 */
public class Result {

    /**
     * The error message associated with the result.
     */
    private String message;

    /**
     * The HTTP status code of the result.
     */
    private int status;

    /**
     * Constructs a new Result object with null values.
     */
    public Result() {
        this.message = null;
        this.status = 0;
    }

    /**
     * Constructs a new Result object with the specified status and null message.
     *
     * @param status the status code of the result
     */
    public Result(int status) {
        this.message = null;
        this.status = status;
    }

    /**
     * Constructs a new Result object with the specified status and message.
     *
     * @param status the status code of the result
     * @param message the message associated with the result
     */
    public Result(int status, String message) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
