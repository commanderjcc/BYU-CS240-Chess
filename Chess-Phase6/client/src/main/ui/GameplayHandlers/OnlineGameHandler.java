package ui.GameplayHandlers;

import Models.AuthToken;
import WSFacade.WSFacade;

/**
 * The OnlineGameHandler class is responsible for handling the game commands that interact with the server. It extends the GameHandler class but adds a WSFacade and AuthToken to the constructor.
 */
public class OnlineGameHandler extends GameHandler {
    protected final WSFacade wsFacade;
    protected final AuthToken authToken;

     /**
     * Constructs an OnlineGameHandler object with the given WSFacade and AuthToken.
     */
    public OnlineGameHandler(WSFacade wsFacade, AuthToken authToken) {
        this.wsFacade = wsFacade;
        this.authToken = authToken;
    }
}
