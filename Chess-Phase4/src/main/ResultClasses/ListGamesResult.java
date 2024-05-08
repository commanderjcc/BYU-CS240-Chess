package ResultClasses;

import Models.Game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents the result of a request to list all games in the system.
 */
public class ListGamesResult extends Result {

    /**
     * The collection of all games to include in the result.
     */
    private List<Game> games;

    /**
     * Creates a new instance of ListGamesResult with a null list of games.
     */
    public ListGamesResult() {
        super();
    }

    /**
     * Creates a new instance of a successful ListGamesResult with the specified list of games and a default status of 200.
     * @param games The list of games to include in the result.
     */
    public ListGamesResult(Collection<Game> games) {
        super(200);
        this.games = new ArrayList<>(games);
    }

    /**
     * Creates a new instance of ListGamesResult with the specified status and message, and a null list of games.
     * @param status The status code of the result.
     * @param message The message associated with the result.
     */
    public ListGamesResult(int status, String message) {
        super(status, message);
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(Collection<Game> games) {
        this.games = new ArrayList<>(games);
    }
}
