package chessGameImpl;

import chess.*;

import java.util.Collection;
import java.util.HashSet;

/**
 * Implementation of a Rook ChessPiece
 */
public class Rook extends CPiece{
    /**
     * Constructor for Rook, with the option to set the type of piece
     * 
     * @param pieceColor The color of the piece
     * @param type the type of the piece
     */
    public Rook(ChessGame.TeamColor pieceColor, PieceType type) {
        super(pieceColor, type);
    }

    /**
     * Constructor for Rook
     * 
     * @param pieceColor the color of the piece
     */
    public Rook(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.ROOK);
    }

    /**
     * @return a Rook with the same team Color
     */
    @Override
    public ChessPiece copy() {
        return new Rook(getTeamColor());
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

        //Up
        for(int i = row + 1; i <= 8; i++) {
            ChessPosition endPosition = new CPosition(i, col);
            if(board.getPiece(endPosition) == null) {
                moves.add(new CMove(myPosition, endPosition, null));
            } else if(board.getPiece(endPosition).getTeamColor() != getTeamColor()) {
                moves.add(new CMove(myPosition, endPosition, null));
                break;
            } else {
                break;
            }
        }

        //Down
        for(int i = row - 1; i >= 1; i--) {
            ChessPosition endPosition = new CPosition(i, col);
            if(board.getPiece(endPosition) == null) {
                moves.add(new CMove(myPosition, endPosition, null));
            } else if(board.getPiece(endPosition).getTeamColor() != getTeamColor()) {
                moves.add(new CMove(myPosition, endPosition, null));
                break;
            } else {
                break;
            }
        }

        //Left
        for(int i = col - 1; i >= 1; i--) {
            ChessPosition endPosition = new CPosition(row, i);
            if(board.getPiece(endPosition) == null) {
                moves.add(new CMove(myPosition, endPosition, null));
            } else if(board.getPiece(endPosition).getTeamColor() != getTeamColor()) {
                moves.add(new CMove(myPosition, endPosition, null));
                break;
            } else {
                break;
            }
        }

        //Right
        for(int i = col + 1; i <= 8; i++) {
            ChessPosition endPosition = new CPosition(row, i);
            if(board.getPiece(endPosition) == null) {
                moves.add(new CMove(myPosition, endPosition, null));
            } else if(board.getPiece(endPosition).getTeamColor() != getTeamColor()) {
                moves.add(new CMove(myPosition, endPosition, null));
                break;
            } else {
                break;
            }
        }

        return moves;
    }
}
