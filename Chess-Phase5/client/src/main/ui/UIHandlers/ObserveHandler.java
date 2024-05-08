package ui.UIHandlers;

import Models.AuthToken;
import Models.Game;
import ResultClasses.JoinGameResult;
import ServerFacade.ServerFacade;
import ui.Printer;

import java.util.List;

/**
 * A class for handling observe game commands from the user.
 */
public class ObserveHandler extends Handler {
    /**
     * Creates a new ObserveHandler.
     *
     * @param serverFacade The server facade to use.
     */
    public ObserveHandler(ServerFacade serverFacade) {
        super(serverFacade);
    }

    public JoinGameResult observe(String[] words, AuthToken authToken, List<Game> previousList) {
        if (words.length != 2) {
            p.reset();
            p.setColor(Printer.Color.RED);
            p.println("Error: Invalid number of arguments");
            p.println("Usage: observe <gameIndex>");
            return null;
        }
        try {
            int gameIndex = Integer.parseInt(words[1]);
            if (gameIndex < 1 || gameIndex > previousList.size()) {
                p.reset();
                p.setColor(Printer.Color.RED);
                p.println("Error: Invalid game index");
                return null;
            }
            Game selectedGame = previousList.get(gameIndex - 1);
            int gameID = selectedGame.getGameID();
            var result = serverFacade.joinGame(authToken, gameID, null);
            result.setGameID(gameID);
            return result;
        } catch (Exception e) {
            p.reset();
            p.setColor(Printer.Color.RED);
            p.println("Error: " + e.getMessage());
            return null;
        }
    }
}
