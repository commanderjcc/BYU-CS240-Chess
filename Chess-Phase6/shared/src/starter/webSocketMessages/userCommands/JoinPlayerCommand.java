package webSocketMessages.userCommands;

import Models.AuthToken;
import chess.ChessGame;

/**
 * Represents a user command to join a chess game as a player.
 */
public class JoinPlayerCommand extends UserGameCommand {
    private Integer gameID;
    private ChessGame.TeamColor playerColor;

    /**
     * Constructs a JoinPlayerCommand object with the specified authentication token, game ID, and player color.
     * 
     * @param authToken the authentication token of the user
     * @param gameID the ID of the game to join
     * @param playerColor the color of the player joining the game
     */
    public JoinPlayerCommand(AuthToken authToken, Integer gameID, ChessGame.TeamColor playerColor) {
        super(authToken);
        this.gameID = gameID;
        this.playerColor = playerColor;
        this.commandType = CommandType.JOIN_PLAYER;
    }

    /**
     * Gets the ID of the game to join.
     * 
     * @return the game ID
     */
    public Integer getGameID() {
        return gameID;
    }

    /**
     * Sets the ID of the game to join.
     * 
     * @param gameID the game ID to set
     */
    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }

    /**
     * Gets the color of the player joining the game.
     * 
     * @return the player color
     */
    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }

    /**
     * Sets the color of the player joining the game.
     * 
     * @param playerColor the player color to set
     */
    public void setPlayerColor(ChessGame.TeamColor playerColor) {
        this.playerColor = playerColor;
    }
}
