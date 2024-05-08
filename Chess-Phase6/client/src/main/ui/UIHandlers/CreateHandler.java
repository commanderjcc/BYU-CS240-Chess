package ui.UIHandlers;

import Models.AuthToken;
import ResultClasses.CreateGameResult;
import ServerFacade.ServerFacade;
import ui.Printer;

/**
 * A class for handling create game commands from the user.
 */
public class CreateHandler extends Handler {
    /**
     * Creates a new CreateHandler.
     *
     * @param serverFacade The server facade to use.
     */
    public CreateHandler(ServerFacade serverFacade) {
        super(serverFacade);
    }

    /**
     * Creates a new game.
     *
     * @param args     The args from the user's input.
     * @param authToken The auth token of the user.
     * @return The result of the create game request.
     */
    public CreateGameResult create(String[] args, AuthToken authToken) {
        if (args.length != 2) {
            p.reset();
            p.setColor(Printer.Color.RED);
            p.println("Error: Invalid number of arguments");
            p.println("Usage: create <gameName>");
            return null;
        }
        try {
            var res = serverFacade.createGame(authToken, args[1]);
            p.reset();
            p.setColor(Printer.Color.GREEN);
            p.println("Game created!");
            return res;
        } catch (Exception e) {
            p.reset();
            p.setColor(Printer.Color.RED);
            p.println("Error: " + e.getMessage());
            return null;
        }
    }
}
