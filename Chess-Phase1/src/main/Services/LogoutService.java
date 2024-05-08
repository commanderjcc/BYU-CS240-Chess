package Services;

import DAOs.AuthDAO;
import DAOs.UserDAO;
import RequestClasses.LogoutRequest;
import ResultClasses.LogoutResult;
import dataAccess.DataAccessException;

public class LogoutService extends Service {

    /**
     * Constructs a new LogoutService object.
     */
    public LogoutService() {
        super();
    }

    /**
     * Constructs a new LogoutService object with the given DAOs.
     * @param authDAO the AuthDAO object to use for the service
     * @param userDAO the UserDAO object to use for the service
     */
    public LogoutService(AuthDAO authDAO, UserDAO userDAO) {
        super(authDAO, null, userDAO);
    }

    /**
     * Logs a user out.
     * @param request the LogoutRequest object containing the authentication token
     * @return a LogoutResult object
     */
    public LogoutResult logout(LogoutRequest request) {
        //Check if authentication token is null
        if (request.getAuthToken() == null) {
            return new LogoutResult(400, "Error: bad request");
        }

        //Check if authentication token is valid
        try {
            authDAO.checkAuthToken(request.getAuthToken());
        } catch (DataAccessException e) {
            return new LogoutResult(401, "Error: unauthorized");
        } catch (Exception e) {
            return new LogoutResult(500, "Error: " + e.getMessage());
        }

        //Delete auth token
        try {
            authDAO.deleteAuthToken(request.getAuthToken());
        } catch (Exception e) {
            return new LogoutResult(500, "Error: " + e.getMessage());
        }

        return new LogoutResult(200);
    }
}
