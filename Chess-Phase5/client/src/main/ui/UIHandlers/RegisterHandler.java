package ui.UIHandlers;

import Exceptions.ResponseException;
import Models.AuthToken;
import ResultClasses.RegisterResult;
import ServerFacade.ServerFacade;
import ui.Printer;

/**
 * A class for handling register commands from the user.
 */
public class RegisterHandler extends Handler {

    /**
     * Creates a new RegisterHandler.
     * @param serverFacade The server facade to use.
     */
    public RegisterHandler(ServerFacade serverFacade) {
        super(serverFacade);
    }

    /**
     * Registers a new user.
     * @param args The args from the user's input.
     * @return The auth token of the user.
     */
    public AuthToken register(String[] args) {
        if (args.length != 4) {
            p.reset();
            p.setColor(Printer.Color.RED);
            p.println("Error: Invalid number of arguments");
            p.println("Usage: register <username> <password> <email>");
            return null;
        }

        try {
            RegisterResult res = serverFacade.register(args[1], args[2], args[3]);
            p.reset();
            p.setColor(Printer.Color.GREEN);
            p.println("Registered!");
            return res.getAuthToken();
        } catch (ResponseException e) {
            p.reset();
            p.setColor(Printer.Color.RED);
            p.println("Error: " + e.getMessage());
            return null;
        }
    }
}
