package ui.UIHandlers;

import Models.AuthToken;
import ServerFacade.ServerFacade;
import ui.Printer;

/**
 * A class for handling logout commands from the user.
 */
public class LogoutHandler extends Handler {
    /**
     * Creates a new LogoutHandler.
     *
     * @param serverFacade The server facade to use.
     */
    public LogoutHandler(ServerFacade serverFacade) {
        super(serverFacade);
    }

    /**
     * Logs the user out.
     *
     * @param authToken The auth token of the user.
     */
    public void logout(AuthToken authToken) {
        try {
            serverFacade.logout(authToken);
        } catch (Exception e) {
            p.reset();
            p.setColor(Printer.Color.RED);
            p.println("Error: " + e.getMessage());
        }
    }
}
