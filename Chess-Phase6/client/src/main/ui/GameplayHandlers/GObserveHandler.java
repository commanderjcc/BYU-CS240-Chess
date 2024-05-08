package ui.GameplayHandlers;

import Exceptions.ResponseException;
import Models.AuthToken;
import WSFacade.WSFacade;

/**
 * The GObserveHandler class is responsible for handling the observe command.
 * It extends the OnlineGameHandler class and provides methods to observe a game.
 */
public class GObserveHandler extends OnlineGameHandler {
    /**
     * Constructs a GObserveHandler object with the given WSFacade and AuthToken.
     *
     * @param wsFacade  The WSFacade to use for sending messages to the server.
     * @param authToken The AuthToken to use for authentication.
     */
    public GObserveHandler(WSFacade wsFacade, AuthToken authToken) {
        super(wsFacade, authToken);
    }

    /**
     * Observes the game with the specified game ID.
     *
     * @param gameId     The ID of the game to observe.
     * @throws ResponseException If an error occurs while sending the observe request.
     */
    public void joinGame(Integer gameId) throws ResponseException {
        wsFacade.sendJoinObserver(authToken, gameId);
    }
}
