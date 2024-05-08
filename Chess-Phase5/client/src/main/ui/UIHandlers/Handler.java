package ui.UIHandlers;

import ServerFacade.ServerFacade;
import ui.Printer;

/**
 * A class for handling commands from the user.
 */
public class Handler {
    protected final Printer p = new Printer();

    protected final ServerFacade serverFacade;

    /**
     * Creates a new Handler.
     *
     * @param serverFacade The server facade to use.
     */
    public Handler(ServerFacade serverFacade) {
        this.serverFacade = serverFacade;
    }
}
