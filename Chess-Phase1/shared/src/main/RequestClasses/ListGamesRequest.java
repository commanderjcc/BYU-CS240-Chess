package RequestClasses;

import Models.AuthToken;

/**
 * Represents a request to list all active games on the server.
 * Inherits from the Request class and includes an optional AuthToken parameter.
 */
public class ListGamesRequest extends Request {

    /**
     * Constructs a new ListGamesRequest object with no authentication token.
     */
    public ListGamesRequest() {
        super();
    }

    /**
     * Constructs a new ListGamesRequest object with the specified authentication token.
     * @param authToken The authentication token to include with the request.
     */
    public ListGamesRequest(AuthToken authToken) {
        super(authToken);
    }
}
