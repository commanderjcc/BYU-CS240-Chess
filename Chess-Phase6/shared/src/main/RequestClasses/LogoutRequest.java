package RequestClasses;

import Models.AuthToken;

/**
 * Represents a request to log out a user from the system.
 */
public class LogoutRequest extends Request {

        /**
         * Constructs a new LogoutRequest object with no authentication token.
         */
        public LogoutRequest() {
            super();
        }

        /**
         * Constructs a new LogoutRequest object with the given authentication token.
         * @param authToken the authentication token for the user to be logged out
         */
        public LogoutRequest(AuthToken authToken) {
            super(authToken);
        }
}
