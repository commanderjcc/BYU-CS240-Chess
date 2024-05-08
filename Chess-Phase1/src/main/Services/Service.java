package Services;

import DAOs.AuthDAO;
import DAOs.GameDAO;
import DAOs.UserDAO;

/**
 * This class represents a service.
 * It provides a constructor and getters and setters for the DAOs.
 */
public class Service {

    /**
     * The AuthDAO object to use for the service.
     */
    protected AuthDAO authDAO;

    /**
     * The GameDAO object to use for the service.
     */
    protected GameDAO gameDAO;

    /**
     * The UserDAO object to use for the service.
     */
    protected UserDAO userDAO;

    /**
     * Constructs a new Service object.
     */
    public Service() {
        authDAO = new AuthDAO();
        gameDAO = new GameDAO();
        userDAO = new UserDAO();
    }

    /**
     * Constructs a new Service object with the given DAOs.
     * 
     * @param authDAO the AuthDAO object to use for the service
     * @param gameDAO the GameDAO object to use for the service
     * @param userDAO the UserDAO object to use for the service
     */
    public Service(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        this.userDAO = userDAO;
    }

    public AuthDAO getAuthDAO() {
        return authDAO;
    }

    public void setAuthDAO(AuthDAO authDAO) {
        this.authDAO = authDAO;
    }

    public GameDAO getGameDAO() {
        return gameDAO;
    }

    public void setGameDAO(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
