package ui.UIHandlers;

import Models.AuthToken;
import Models.Game;
import ResultClasses.ListGamesResult;
import ServerFacade.ServerFacade;
import ui.Printer;

import java.util.List;

/**
 * A class for handling list games commands from the user.
 */
public class ListHandler extends Handler {
    /**
     * Creates a new ListHandler.
     *
     * @param serverFacade The server facade to use.
     */
    public ListHandler(ServerFacade serverFacade) {
        super(serverFacade);
    }

    /**
     * Lists all games.
     *
     * @param authToken The auth token of the user.
     * @return The result of the list games request.
     */
    public List<Game> list(AuthToken authToken) {
        try {
            ListGamesResult res = serverFacade.listGames(authToken);
            p.reset();
            p.setColor(Printer.Color.GREEN);
            p.println("Games:");
            int index = 1;
            for (Game game : res.getGames()) {
                p.println(index + ") " + game.toString());
                index++;
            }
            return res.getGames();
        } catch (Exception e) {
            p.reset();
            p.setColor(Printer.Color.RED);
            p.println("Error: " + e.getMessage());
            return null;
        }

    }
}
