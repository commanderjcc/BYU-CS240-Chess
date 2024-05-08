package Services;

import DAOs.AuthDAO;
import DAOs.GameDAO;
import DAOs.UserDAO;
import Models.Game;
import dataAccess.DataAccessException;

/**
 * The GameService class provides methods for accessing and manipulating game data.
 */
public class GameService extends Service {
    public GameService(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        this.userDAO = userDAO;
    }

    /**
     * Retrieves a game by its ID.
     *
     * @param gameID the ID of the game to retrieve
     * @return the Game object corresponding to the given ID
     * @throws DataAccessException if there is an error accessing the data
     */
    public Game getGame(Integer gameID) throws DataAccessException {
        return gameDAO.getGame(gameID);
    }

    /**
     * Updates the specified game.
     *
     * @param game the game to update
     * @throws DataAccessException if there is an error accessing the data
     */
    public void updateGame(Game game) throws DataAccessException {
        gameDAO.updateGame(game);
    }
}
