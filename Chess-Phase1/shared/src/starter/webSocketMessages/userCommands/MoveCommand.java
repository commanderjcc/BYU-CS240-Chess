package webSocketMessages.userCommands;

import Models.AuthToken;
import chess.ChessMove;

/**
 * Represents a user command to make a move in a chess game.
 * Extends the UserGameCommand class.
 */
public class MoveCommand extends UserGameCommand {
    private Integer gameID;
    private ChessMove move;

    /**
     * Constructs a MoveCommand object with the specified authentication token, game ID, and chess move.
     * Sets the command type to MAKE_MOVE.
     *
     * @param authToken The authentication token of the user.
     * @param gameID The ID of the chess game.
     * @param move The chess move to be made.
     */
    public MoveCommand(AuthToken authToken, Integer gameID, ChessMove move) {
        super(authToken);
        this.gameID = gameID;
        this.move = move;
        this.commandType = CommandType.MAKE_MOVE;
    }

    /**
     * Returns the ID of the chess game.
     *
     * @return The game ID.
     */
    public Integer getGameID() {
        return gameID;
    }

    /**
     * Sets the ID of the chess game.
     *
     * @param gameID The game ID to be set.
     */
    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }

    /**
     * Returns the chess move.
     *
     * @return The chess move.
     */
    public ChessMove getMove() {
        return move;
    }

    /**
     * Sets the chess move.
     *
     * @param move The chess move to be set.
     */
    public void setMove(ChessMove move) {
        this.move = move;
    }
}
