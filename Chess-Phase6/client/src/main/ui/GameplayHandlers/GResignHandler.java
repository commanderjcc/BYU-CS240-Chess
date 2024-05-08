package ui.GameplayHandlers;

import Exceptions.ResponseException;
import Models.AuthToken;
import WSFacade.WSFacade;

/**
 * The GResignHandler class is responsible for handling the resign command.
 * It extends the OnlineGameHandler class and provides methods to resign a game.
 */
public class GResignHandler extends OnlineGameHandler {
    /**
     * Constructs a GResignHandler object with the given WSFacade and AuthToken.
     *
     * @param wsFacade  The WSFacade to use for sending messages to the server.
     * @param authToken The AuthToken to use for authentication.
     */
    public GResignHandler(WSFacade wsFacade, AuthToken authToken) {
        super(wsFacade, authToken);
    }

    /**
     * Resigns the game with the specified game ID.
     *
     * @param gameID     The ID of the game to resign.
     * @throws ResponseException If an error occurs while sending the resign request.
     */
    public void resign(Integer gameID) throws ResponseException {
        wsFacade.sendResign(authToken, gameID);
    }
}
