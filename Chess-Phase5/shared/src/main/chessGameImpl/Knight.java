package chessGameImpl;

import chess.*;

import java.util.Collection;
import java.util.HashSet;

/**
 * Implementation of the ChessPiece interface representing a knight
 */
public class Knight extends CPiece{
    /**
     * Constructor for Knight, with the option to set the type of piece
     * 
     * @param pieceColor The color of the piece
     * @param type the type of piece
     */
    public Knight(ChessGame.TeamColor pieceColor, PieceType type) {
        super(pieceColor, type);
    }

    /**
     * Constructor for Knight
     * 
     * @param pieceColor The color of the piece
     */
    public Knight(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.KNIGHT);
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @param board  The current state of the board
     * @param myPosition  the location of the piece to generate a moveset for
     * @return Collection of valid moves
     */
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> moves = new HashSet<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        //UpRight
        if(row <= 6 && col <= 7) {
            ChessPosition endPosition = new CPosition(row + 2, col + 1);
            if(board.getPiece(endPosition) == null || board.getPiece(endPosition).getTeamColor() != getTeamColor()) {
                moves.add(new CMove(myPosition, endPosition, null));
            }
        }

        //UpLeft
        if(row <= 6 && col >= 2) {
            ChessPosition endPosition = new CPosition(row + 2, col - 1);
            if(board.getPiece(endPosition) == null || board.getPiece(endPosition).getTeamColor() != getTeamColor()) {
                moves.add(new CMove(myPosition, endPosition, null));
            }
        }

        //DownRight
        if(row >= 3 && col <= 7) {
            ChessPosition endPosition = new CPosition(row - 2, col + 1);
            if(board.getPiece(endPosition) == null || board.getPiece(endPosition).getTeamColor() != getTeamColor()) {
                moves.add(new CMove(myPosition, endPosition, null));
            }
        }

        //DownLeft
        if(row >= 3 && col >= 2) {
            ChessPosition endPosition = new CPosition(row - 2, col - 1);
            if(board.getPiece(endPosition) == null || board.getPiece(endPosition).getTeamColor() != getTeamColor()) {
                moves.add(new CMove(myPosition, endPosition, null));
            }
        }

        //RightUp
        if(row <= 7 && col <= 6) {
            ChessPosition endPosition = new CPosition(row + 1, col + 2);
            if(board.getPiece(endPosition) == null || board.getPiece(endPosition).getTeamColor() != getTeamColor()) {
                moves.add(new CMove(myPosition, endPosition, null));
            }
        }

        //RightDown
        if(row >= 2 && col <= 6) {
            ChessPosition endPosition = new CPosition(row - 1, col + 2);
            if(board.getPiece(endPosition) == null || board.getPiece(endPosition).getTeamColor() != getTeamColor()) {
                moves.add(new CMove(myPosition, endPosition, null));
            }
        }

        //LeftUp
        if(row <= 7 && col >= 3) {
            ChessPosition endPosition = new CPosition(row + 1, col - 2);
            if(board.getPiece(endPosition) == null || board.getPiece(endPosition).getTeamColor() != getTeamColor()) {
                moves.add(new CMove(myPosition, endPosition, null));
            }
        }

        //LeftDown
        if(row >= 2 && col >= 3) {
            ChessPosition endPosition = new CPosition(row - 1, col - 2);
            if(board.getPiece(endPosition) == null || board.getPiece(endPosition).getTeamColor() != getTeamColor()) {
                moves.add(new CMove(myPosition, endPosition, null));
            }
        }

        return moves;
    }

    /**
     * @return A Knight with the same team color
     */
    @Override
    public ChessPiece copy() {
        return new Knight(getTeamColor());
    }
}
