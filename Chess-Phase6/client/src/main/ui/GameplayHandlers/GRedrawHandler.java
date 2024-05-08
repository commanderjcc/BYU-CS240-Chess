package ui.GameplayHandlers;

import chess.ChessGame;

/**
 * The GRedrawHandler class is responsible for handling the redraw command.
 * It extends the GameHandler class and provides methods to redraw the chess board.
 */
public class GRedrawHandler extends GameHandler {
    /**
     * The team color of the chess game.
     */
    private final ChessGame.TeamColor teamColor;

    /**
     * Creates a new Handler.
     *
     * @param teamColor The team color to use.
     */
    public GRedrawHandler(ChessGame.TeamColor teamColor) {
        this.teamColor = teamColor;
    }

    /**
     * Redraws the chess board.
     *
     * @param game The current game state.
     */
    public void redraw(ChessGame game) {
        cbp.printBoard(game.getBoard(), teamColor);
    }
}
