package ui.UIHandlers;

import Exceptions.ResponseException;
import ResultClasses.LoginResult;
import ServerFacade.ServerFacade;
import ui.Printer;

/**
 * A class for handling login commands from the user.
 */
public class LoginHandler extends Handler {
    /**
     * Creates a new LoginHandler.
     *
     * @param serverFacade The server facade to use.
     */
    public LoginHandler(ServerFacade serverFacade) {
        super(serverFacade);
    }

    /**
     * Logs the user in.
     * @param args The args from the user's input.
     * @return The auth token of the user.
     */
    public Models.AuthToken login(String[] args) {
        if (args.length != 3) {
            p.reset();
            p.setColor(Printer.Color.RED);
            p.println("Error: Invalid number of arguments");
            p.println("Usage: login <username> <password>");
            return null;
        }

        try {
            LoginResult res = serverFacade.login(args[1], args[2]);
            if (res.getStatus() != 200) throw new ResponseException(res.getStatus(), res.getMessage());
            p.reset();
            p.setColor(Printer.Color.GREEN);
            p.println("Logged in!");
            return res.getAuthToken();
        } catch (ResponseException e) {
            p.reset();
            p.setColor(Printer.Color.RED);
            p.println("Error: " + e.getMessage());
            return null;
        }
    }
}
