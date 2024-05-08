package Services;

import DAOs.AuthDAO;
import DAOs.GameDAO;
import DAOs.UserDAO;
import RequestClasses.JoinGameRequest;
import ResultClasses.JoinGameResult;
import dataAccess.DataAccessException;

/**
 * This class represents a service that joins a game.
 * It extends the Service class and provides a method to join a game.
 * The joinGame method takes a JoinGameRequest object as input and returns a JoinGameResult object.
 * This class can be used to join a game.
 */
public class JoinGameService extends Service {

        /**
         * Constructs a new JoinGameService object.
         */
        public JoinGameService() {
            super();
        }

        /**
         * Constructs a new JoinGameService object with the given DAOs.
         * @param authDAO the AuthDAO object to use for the service
         * @param gameDAO the GameDAO object to use for the service
         * @param userDAO the UserDAO object to use for the service
         */
        public JoinGameService(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO) {
            super(authDAO, gameDAO, userDAO);
        }

        /**
         * Joins a game.
         * @param request the JoinGameRequest object containing the authentication token and game name
         * @return a JoinGameResult object
         */
        public JoinGameResult joinGame(JoinGameRequest request) {
            //Check if authentication token or gameID are null
            if (request.getAuthToken() == null || request.getGameID() == 0) {
                return new JoinGameResult(400, "Error: bad request");
            }

            //Check if authentication token is valid
            try {
                authDAO.checkAuthToken(request.getAuthToken());
            } catch (Exception e) {
                return new JoinGameResult(401, "Error: unauthorized");
            }

            //Check if gameID exists
            try {
                gameDAO.getGame(request.getGameID());
            } catch (Exception e) {
                return new JoinGameResult(400, "Error: game does not exist");
            }

            // Discover Username
            String username;
            try {
                username = authDAO.getUsername(request.getAuthToken());
            } catch (Exception e) {
                return new JoinGameResult(500, "Error: " + e.getMessage());
            }

            //Join game
            try {
                gameDAO.claimSpot(request.getGameID(), username, request.getPlayerColor());
                return new JoinGameResult(200, request.getPlayerColor());
            } catch (DataAccessException e) {
                return new JoinGameResult(403, "Error: already taken");
            } catch (Exception e) {
                return new JoinGameResult(500, "Error: " + e.getMessage());
            }
        }
}
