package ui.GameplayHandlers;

import Exceptions.ResponseException;
import Models.AuthToken;
import WSFacade.WSFacade;

/**
 * The GLeaveHandler class is responsible for handling the leave command.
 * It extends the OnlineGameHandler class and provides methods to leave a game.
 */
public class GLeaveHandler extends OnlineGameHandler {
    /**
     * Constructs a GLeaveHandler object with the given WSFacade and AuthToken.
     *
     * @param wsFacade  The WSFacade to use for sending messages to the server.
     * @param authToken The AuthToken to use for authentication.
     */
    public GLeaveHandler(WSFacade wsFacade, AuthToken authToken) {
        super(wsFacade, authToken);
    }

    /**
     * Leaves the game with the specified game ID.
     *
     * @param gameID     The ID of the game to leave.
     * @throws ResponseException If an error occurs while sending the leave request.
     */
    public void leave(Integer gameID) throws ResponseException {
        wsFacade.sendLeaveGame(authToken, gameID);
    }
}
