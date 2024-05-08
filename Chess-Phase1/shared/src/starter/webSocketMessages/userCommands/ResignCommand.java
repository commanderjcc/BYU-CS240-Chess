package webSocketMessages.userCommands;

import Models.AuthToken;

/**
 * Represents a command to resign from a game.
 * Extends the UserGameCommand class.
 */
public class ResignCommand extends UserGameCommand {
    private Integer gameID;

    /**
     * Constructs a ResignCommand object with the specified authentication token and game ID.
     * Sets the command type to RESIGN.
     *
     * @param authToken The authentication token of the user.
     * @param gameID The ID of the game to resign from.
     */
    public ResignCommand(AuthToken authToken, Integer gameID) {
        super(authToken);
        this.gameID = gameID;
        this.commandType = CommandType.RESIGN;
    }

    /**
     * Returns the ID of the game to resign from.
     *
     * @return The game ID.
     */
    public Integer getGameID() {
        return gameID;
    }

    /**
     * Sets the ID of the game to resign from.
     *
     * @param gameID The game ID to set.
     */
    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }
}
