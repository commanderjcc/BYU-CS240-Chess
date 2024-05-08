package RequestClasses;

import Models.AuthToken;

/**
 * Represents a request to clear the database.
 */
public class ClearDBRequest extends Request {
    /**
     * Constructs a new ClearDBRequest object with no authentication token.
     */
    public ClearDBRequest() {
        super();
    }

    /**
     * Constructs a new ClearDBRequest object with the given authentication token.
     * @param authToken the authentication token to use for the request
     */
    public ClearDBRequest(AuthToken authToken) {
        super(authToken);
    }
}
