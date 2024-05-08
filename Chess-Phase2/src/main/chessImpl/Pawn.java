package chessImpl;

import chess.*;

import java.util.HashSet;
import java.util.Set;

public class Pawn extends CPiece {
    public Pawn(ChessGame.TeamColor pieceColor, PieceType type) {
        super(pieceColor, type);
    }

    public Pawn(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.PAWN);
    }

    /**
     * @return
     */
    @Override
    public ChessPiece copy() {
        return new Pawn(getTeamColor());
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
    public Set<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> moves = new HashSet<>();
        int step = (getTeamColor() == ChessGame.TeamColor.WHITE) ? 1 : -1;
        int promotionRow = (getTeamColor() == ChessGame.TeamColor.WHITE) ? 7 : 2;
        int startRow = (getTeamColor() == ChessGame.TeamColor.WHITE) ? 2 : 7;
        int row = myPosition.getRow();
        int column = myPosition.getColumn();


        //First move
        CPosition oneAhead = new CPosition(row + step, column);
        CPosition twoAhead = new CPosition(row + 2 * step, column);
        if(row == startRow) {
            if(board.getPiece(twoAhead) == null && board.getPiece(oneAhead) == null) {
                moves.add(new CMove(myPosition, twoAhead, null));
            }
        }

        //Standard move forward
        CPosition endPosition = new CPosition(row + step, column);
        if (row == promotionRow && board.getPiece(endPosition) == null) {
            //Promotion
            moves.add(new CMove(myPosition, endPosition, PieceType.ROOK));
            moves.add(new CMove(myPosition, endPosition, PieceType.KNIGHT));
            moves.add(new CMove(myPosition, endPosition, PieceType.BISHOP));
            moves.add(new CMove(myPosition, endPosition, PieceType.QUEEN));
        } else if (board.getPiece(endPosition) == null) {
            moves.add(new CMove(myPosition, endPosition, null));
        }

        //Capture left
        if (column > 1) {
            CPosition left = new CPosition(row + step, column - 1);
            if (row == promotionRow && board.getPiece(left) != null && board.getPiece(left).getTeamColor() != getTeamColor()) {
                moves.add(new CMove(myPosition, left, PieceType.ROOK));
                moves.add(new CMove(myPosition, left, PieceType.KNIGHT));
                moves.add(new CMove(myPosition, left, PieceType.BISHOP));
                moves.add(new CMove(myPosition, left, PieceType.QUEEN));
            } else if (board.getPiece(left) != null && board.getPiece(left).getTeamColor() != getTeamColor()) {
                moves.add(new CMove(myPosition, left, null));
            }
        }

        //Capture right
        if (column < 8) {
            CPosition right = new CPosition(row + step, column + 1);
            if (row == promotionRow && board.getPiece(right) != null && board.getPiece(right).getTeamColor() != getTeamColor()) {
                moves.add(new CMove(myPosition, right, PieceType.ROOK));
                moves.add(new CMove(myPosition, right, PieceType.KNIGHT));
                moves.add(new CMove(myPosition, right, PieceType.BISHOP));
                moves.add(new CMove(myPosition, right, PieceType.QUEEN));
            } else if (board.getPiece(right) != null && board.getPiece(right).getTeamColor() != getTeamColor()) {
                moves.add(new CMove(myPosition, right, null));
            }
        }

        return moves;
    }
}
