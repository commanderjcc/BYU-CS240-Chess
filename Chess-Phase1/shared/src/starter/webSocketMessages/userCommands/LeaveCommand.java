package webSocketMessages.userCommands;

import Models.AuthToken;

/**
 * Represents a user command to leave a game.
 */
public class LeaveCommand extends UserGameCommand {
    private Integer gameID;

    /**
     * Constructs a LeaveCommand object with the specified authentication token and game ID.
     * 
     * @param authToken the authentication token of the user
     * @param gameID the ID of the game to leave
     */
    public LeaveCommand(AuthToken authToken, Integer gameID) {
        super(authToken);
        this.gameID = gameID;
        this.commandType = CommandType.LEAVE;
    }

    /**
     * Gets the ID of the game to leave.
     * 
     * @return the game ID
     */
    public Integer getGameID() {
        return gameID;
    }

    /**
     * Sets the ID of the game to leave.
     * 
     * @param gameID the game ID to set
     */
    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }
}
