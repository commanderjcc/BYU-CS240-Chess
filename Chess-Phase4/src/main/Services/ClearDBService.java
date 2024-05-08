
package Services;

import DAOs.AuthDAO;
import DAOs.GameDAO;
import DAOs.UserDAO;
import RequestClasses.ClearDBRequest;
import ResultClasses.ClearDBResult;

/**
 * This class represents a service that clears the database.
 * It extends the Service class and provides a method to clear the database.
 * The clearDB method takes a ClearDBRequest object as input and returns a ClearDBResult object.
 * This class can be used to clear the database of all data.
 */
public class ClearDBService extends Service {
    /**
     * Constructs a new ClearDBService object.
     */
    public ClearDBService() {
        super();
    }

    /**
     * Constructs a new ClearDBService object with the given DAOs.
     * @param authDAO the AuthDAO object to use for the service
     * @param gameDAO the GameDAO object to use for the service
     * @param userDAO the UserDAO object to use for the service
     */
    public ClearDBService(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO) {
        super(authDAO, gameDAO, userDAO);
    }

    /**
     * Clears the database of all data.
     * @param request the ClearDBRequest object containing the authentication token
     * @return a ClearDBResult object
     */
    public ClearDBResult clearDB(ClearDBRequest request) {
        try {
            authDAO.clear();
            gameDAO.clear();
            userDAO.clear();
        } catch (Exception e) {
            return new ClearDBResult(500, "Error: " + e.getMessage());
        }
        return new ClearDBResult(200);
    }
}
