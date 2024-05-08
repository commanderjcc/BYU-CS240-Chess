package chessGameImpl;

import chess.*;

import java.util.Collection;
import java.util.HashSet;

/**
 * Represents a King chess piece
 */
public class King extends CPiece {
    /**
     * Constructor for King, with the option to set the type of piece
     * @param pieceColor The color of the King piece
     * @param type The type of the King piece
     */
    public King(ChessGame.TeamColor pieceColor, PieceType type) {
        super(pieceColor, type);
    }

    /**
     * Constructor for King
     * @param pieceColor The color of the King piece
     */
    public King(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.KING);
    }

    /**
     * @return a Copy of the King piece
     */
    @Override
    public ChessPiece copy() {
        return new King(getTeamColor());
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @param board  The current state of the board
     * @param myPosition  The position of the piece to generate a moveset for
     * @return Collection of valid moves
     */
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> moves = new HashSet<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        //Standard moves
        //UpRight
        if(row <= 7 && col <= 7) {
            ChessPosition endPosition = new CPosition(row + 1, col + 1);
            if(board.getPiece(endPosition) == null || board.getPiece(endPosition).getTeamColor() != getTeamColor()) {
                moves.add(new CMove(myPosition, endPosition, null));
            }
        }

        //UpLeft
        if(row <= 7 && col >= 2) {
            ChessPosition endPosition = new CPosition(row + 1, col - 1);
            if(board.getPiece(endPosition) == null || board.getPiece(endPosition).getTeamColor() != getTeamColor()) {
                moves.add(new CMove(myPosition, endPosition, null));
            }
        }

        //DownRight
        if(row >= 2 && col <= 7) {
            ChessPosition endPosition = new CPosition(row - 1, col + 1);
            if(board.getPiece(endPosition) == null || board.getPiece(endPosition).getTeamColor() != getTeamColor()) {
                moves.add(new CMove(myPosition, endPosition, null));
            }
        }

        //DownLeft
        if(row >= 2 && col >= 2) {
            ChessPosition endPosition = new CPosition(row - 1, col - 1);
            if(board.getPiece(endPosition) == null || board.getPiece(endPosition).getTeamColor() != getTeamColor()) {
                moves.add(new CMove(myPosition, endPosition, null));
            }
        }

        //Up
        if(row <= 7) {
            ChessPosition endPosition = new CPosition(row + 1, col);
            if(board.getPiece(endPosition) == null || board.getPiece(endPosition).getTeamColor() != getTeamColor()) {
                moves.add(new CMove(myPosition, endPosition, null));
            }
        }

        //Down
        if(row >= 2) {
            ChessPosition endPosition = new CPosition(row - 1, col);
            if(board.getPiece(endPosition) == null || board.getPiece(endPosition).getTeamColor() != getTeamColor()) {
                moves.add(new CMove(myPosition, endPosition, null));
            }
        }

        //Left
        if(col >= 2) {
            ChessPosition endPosition = new CPosition(row, col - 1);
            if(board.getPiece(endPosition) == null || board.getPiece(endPosition).getTeamColor() != getTeamColor()) {
                moves.add(new CMove(myPosition, endPosition, null));
            }
        }

        //Right
        if(col <= 7) {
            ChessPosition endPosition = new CPosition(row, col + 1);
            if (board.getPiece(endPosition) == null || board.getPiece(endPosition).getTeamColor() != getTeamColor()) {
                moves.add(new CMove(myPosition, endPosition, null));
            }
        }

        return moves;
    }
}
