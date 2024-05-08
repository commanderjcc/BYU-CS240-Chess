package webSocketMessages.userCommands;

import Models.AuthToken;

/**
 * Represents a command to join a game as an observer.
 * Extends the UserGameCommand class.
 */
public class JoinObserverCommand extends UserGameCommand {
    private Integer gameID;

    /**
     * Constructs a JoinObserverCommand object with the specified authentication token and game ID.
     * Sets the command type to JOIN_OBSERVER.
     *
     * @param authToken The authentication token of the user.
     * @param gameID The ID of the game to join as an observer.
     */
    public JoinObserverCommand(AuthToken authToken, Integer gameID) {
        super(authToken);
        this.gameID = gameID;
        this.commandType = CommandType.JOIN_OBSERVER;
    }

    /**
     * Gets the ID of the game to join as an observer.
     *
     * @return The game ID.
     */
    public Integer getGameID() {
        return gameID;
    }

    /**
     * Sets the ID of the game to join as an observer.
     *
     * @param gameID The game ID to set.
     */
    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }
}
