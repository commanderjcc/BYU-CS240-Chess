package chessImpl;

import chess.*;

import java.util.Collection;
import java.util.HashSet;

public class Knight extends CPiece{
    public Knight(ChessGame.TeamColor pieceColor, PieceType type) {
        super(pieceColor, type);
    }

    public Knight(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.KNIGHT);
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @param board
     * @param myPosition
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
     * @return
     */
    @Override
    public ChessPiece copy() {
        return new Knight(getTeamColor());
    }
}
