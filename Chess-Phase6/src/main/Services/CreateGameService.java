package Services;

import DAOs.AuthDAO;
import DAOs.GameDAO;
import DAOs.UserDAO;
import RequestClasses.CreateGameRequest;
import ResultClasses.CreateGameResult;
import chessGameImpl.CGame;
import dataAccess.DataAccessException;

/**
 * This class represents a service that creates a new game.
 * It extends the Service class and provides a method to create a new game.
 * The createGame method takes a CreateGameRequest object as input and returns a CreateGameResult object.
 * This class can be used to create a new game.
 */
public class CreateGameService extends Service {

    /**
     * Constructs a new CreateGameService object.
     */
    public CreateGameService() {
        super();
    }

    /**
     * Constructs a new CreateGameService object with the given DAOs.
     * @param authDAO the AuthDAO object to use for the service
     * @param gameDAO the GameDAO object to use for the service
     * @param userDAO the UserDAO object to use for the service
     */
    public CreateGameService(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO) {
        super(authDAO, gameDAO, userDAO);
    }

    /**
     * Creates a new game.
     * @param request the CreateGameRequest object containing the authentication token and game name
     * @return a CreateGameResult object
     */
    public CreateGameResult createGame(CreateGameRequest request) {
        //Check if authentication token or gameID are null
        if (request.getAuthToken() == null || request.getGameName() == null) {
            return new CreateGameResult(400, "Error: bad request");
        }

        //Check if authentication token is valid
        try {
            authDAO.checkAuthToken(request.getAuthToken());
        } catch (DataAccessException e) {
            return new CreateGameResult(401, "Error: unauthorized");
        }

        //Create game
        try {
            var game = new Models.Game();
            game.setGameName(request.getGameName());
            game.setGameID(game.generateGameID());
            game.setGame(new CGame());
            gameDAO.addGame(game);
            return new CreateGameResult(game.getGameID());
        } catch (Exception e) {
            return new CreateGameResult(500, "Error: " + e.getMessage());
        }
    }
}
