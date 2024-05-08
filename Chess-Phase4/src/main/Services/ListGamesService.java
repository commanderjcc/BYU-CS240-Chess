package Services;

import DAOs.AuthDAO;
import DAOs.GameDAO;
import DAOs.UserDAO;
import RequestClasses.ListGamesRequest;
import ResultClasses.ListGamesResult;

/**
 * This class represents a service that lists all active games on the server.
 * It extends the Service class and provides a method to list all active games.
 * The listGames method takes a ListGamesRequest object as input and returns a ListGamesResult object.
 * This class can be used to list all active games.
 */
public class ListGamesService extends Service {

        /**
         * Constructs a new ListGamesService object.
         */
        public ListGamesService() {
            super();
        }

        /**
         * Constructs a new ListGamesService object with the given DAOs.
         * @param authDAO the AuthDAO object to use for the service
         * @param gameDAO the GameDAO object to use for the service
         * @param userDAO the UserDAO object to use for the service
         */
        public ListGamesService(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO) {
            super(authDAO, gameDAO, userDAO);
        }

        /**
         * Lists all active games.
         * @param request the ListGamesRequest object containing the authentication token
         * @return a ListGamesResult object
         */
        public ListGamesResult listGames(ListGamesRequest request) {
            //Check if authentication token is null
            if (request.getAuthToken() == null) {
                return new ListGamesResult(400, "Error: bad request");
            }

            //Check if authentication token is valid
            try {
                var token = authDAO.checkAuthToken(request.getAuthToken());
            } catch (Exception e) {
                return new ListGamesResult(401, "Error: unauthorized");
            }

            //List all active games
            try {
                var games = gameDAO.getAllGames();
                return new ListGamesResult(games);
            } catch (Exception e) {
                return new ListGamesResult(500, "Error: " + e.getMessage());
            }
        }
}
