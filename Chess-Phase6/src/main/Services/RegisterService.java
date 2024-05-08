package Services;

import DAOs.AuthDAO;
import DAOs.UserDAO;
import Models.AuthToken;
import Models.User;
import RequestClasses.RegisterRequest;
import ResultClasses.RegisterResult;
import dataAccess.DataAccessException;

/**
 * This class represents a service that registers a user.
 * It extends the Service class and provides a method to register a user.
 * The register method takes a RegisterRequest object as input and returns a RegisterResult object.
 * This class can be used to register a user.
 */
public class RegisterService extends Service {

    /**
     * Constructs a new RegisterService object.
     */
    public RegisterService() {
        super();
    }

    /**
     * Constructs a new RegisterService object with the given DAOs.
     * @param authDAO the AuthDAO object to use for the service
     * @param userDAO the UserDAO object to use for the service
     */
    public RegisterService(AuthDAO authDAO, UserDAO userDAO) {
        super(authDAO, null, userDAO);
    }

    /**
     * Registers a user.
     * @param request the RegisterRequest object containing the username and password
     * @return a RegisterResult object
     */
    public RegisterResult register(RegisterRequest request) {
        //Check if username or password or email are null
        if (request.getUsername() == null || request.getPassword() == null || request.getEmail() == null) {
            return new RegisterResult(400, "Error: bad request");
        }

        //Check if username is already taken
        try {
            var user = new User(request.getUsername(), request.getPassword(), request.getEmail());
            userDAO.addUser(user);
        } catch (DataAccessException e) {
            return new RegisterResult(403, "Error: already taken");
        } catch (Exception e) {
            return new RegisterResult(500, "Error: " + e.getMessage());
        }

        //Add auth token
        var token = new AuthToken(request.getUsername());
        try {
            authDAO.addAuthToken(token);
        } catch (Exception e) {
            return new RegisterResult(500, "Error: " + e.getMessage());
        }
        return new RegisterResult(token, request.getUsername());
    }
}
