package ui.GameplayHandlers;

import chess.ChessGame;
import chess.ChessPosition;
import chessGameImpl.CPosition;
import ui.Printer;

/**
 * The GHighlightHelper class is responsible for highlighting the valid moves of a chess piece on the game board.
 * It extends the GameHandler class and provides methods to extract the position from passed args,
 * as well as highlight the valid moves of a given position.
 */
public class GHighlightHelper extends GameHandler {
    /**
     * Constructs a GHighlightHelper object with the given team color.
     * @param teamColor the teamColor to draw the board with
     */    
    public GHighlightHelper(ChessGame.TeamColor teamColor) {
        this.teamColor = teamColor;
    }

    /**
     * The team color of the chess game.
     */
    private final ChessGame.TeamColor teamColor;

    /**
     * Extracts the position from the passed args and highlights the valid moves of the given position.
     * @param game The current game state.
     * @param args The arguments passed to the command.
     */
    public void extractPositionAndView(ChessGame game, String[] args) {
        if (args.length != 2) {
            p.reset();
            p.setColor(Printer.Color.RED);
            p.println("Invalid number of arguments");
            new GHelpHandler().help();
            return;
        }

        // Convert from letter-number to x-y
        var position = args[1];
        var y = position.charAt(0) - 'a' + 1;
        var x = position.charAt(1) - '1' + 1;

        var cPosition = new CPosition(x, y);

        highlightMoves(game, cPosition);
    }

    /**
     * Highlights the valid moves of the given position.
     * @param game The current game state.
     * @param position The position to highlight the valid moves of.
     */
    public void highlightMoves(ChessGame game, ChessPosition position) {
        var validMoves = game.validMoves(position);
        cbp.printBoardHighlighted(game.getBoard(), teamColor, position, validMoves);
    }

}
