package RequestClasses;

import Models.AuthToken;

/**
 * This class represents a request object that contains an authentication token.
 * It can be used to authenticate a user and perform authorized actions.
 */
public class Request {

    /**
     * The authentication token to use for the request.
     */
    private AuthToken authToken;

    /**
     * Constructs a new Request object with no authentication token.
     */
    public Request() {
        this.authToken = null;
    }

    /**
     * Constructs a new Request object with the given authentication token.
     * @param authToken the authentication token to use for the request
     */
    public Request(AuthToken authToken) {
        this.authToken = authToken;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
