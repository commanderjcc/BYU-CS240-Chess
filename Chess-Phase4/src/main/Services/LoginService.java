package Services;

import DAOs.AuthDAO;
import DAOs.UserDAO;
import Models.AuthToken;
import RequestClasses.LoginRequest;
import ResultClasses.LoginResult;
import dataAccess.DataAccessException;

/**
 * This class represents a service that logs a user in.
 * It extends the Service class and provides a method to log a user in.
 * The login method takes a LoginRequest object as input and returns a LoginResult object.
 * This class can be used to log a user in.
 */
public class LoginService extends Service {

    /**
     * Constructs a new LoginService object.
     */
    public LoginService() {
        super();
    }

    /**
     * Constructs a new LoginService object with the given DAOs.
     * @param authDAO the AuthDAO object to use for the service
     * @param userDAO the UserDAO object to use for the service
     */
    public LoginService(AuthDAO authDAO, UserDAO userDAO) {
        super(authDAO, null, userDAO);
    }

    /**
     * Logs a user in.
     * @param request the LoginRequest object containing the username and password
     * @return a LoginResult object
     */
    public LoginResult login(LoginRequest request) {
        //Check if username or password are null
        if (request.getUsername() == null || request.getPassword() == null) {
            return new LoginResult(400, "Error: bad request");
        }

        //Check if username and password match
        try {
            var user = userDAO.getUser(request.getUsername());
            if (user == null || !user.getPassword().equals(request.getPassword())) {
                return new LoginResult(401, "Error: unauthorized");
            }
        } catch (DataAccessException e) {
            return new LoginResult(401, "Error: unauthorized");
        } catch (Exception e) {
            return new LoginResult(500, "Error: " + e.getMessage());
        }
        //Add auth token
        var token = new AuthToken(request.getUsername());
        try {
            authDAO.addAuthToken(token);
        } catch (Exception e) {
            return new LoginResult(500, "Error: " + e.getMessage());
        }
        return new LoginResult(token);
    }
}
