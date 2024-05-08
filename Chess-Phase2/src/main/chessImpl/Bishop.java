package chessImpl;

import chess.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Bishop extends CPiece{
    public Bishop(ChessGame.TeamColor pieceColor, PieceType type) {
        super(pieceColor, type);
    }

    public Bishop(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.BISHOP);
    }

    /**
     * @return
     */
    @Override
    public ChessPiece copy() {
        return new Bishop(getTeamColor());
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
        for(int i = 1; i <= 8; i++) {
            if(row + i <= 8 && col + i <= 8) {
                ChessPosition endPosition = new CPosition(row + i, col + i);
                if(board.getPiece(endPosition) == null) {
                    moves.add(new CMove(myPosition, endPosition, null));
                } else if(board.getPiece(endPosition).getTeamColor() != getTeamColor()) {
                    moves.add(new CMove(myPosition, endPosition, null));
                    break;
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        //UpLeft
        for(int i = 1; i <= 8; i++) {
            if(row + i <= 8 && col - i >= 1) {
                ChessPosition endPosition = new CPosition(row + i, col - i);
                if(board.getPiece(endPosition) == null) {
                    moves.add(new CMove(myPosition, endPosition, null));
                } else if(board.getPiece(endPosition).getTeamColor() != getTeamColor()) {
                    moves.add(new CMove(myPosition, endPosition, null));
                    break;
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        //DownRight
        for(int i = 1; i <= 8; i++) {
            if(row - i >= 1 && col + i <= 8) {
                ChessPosition endPosition = new CPosition(row - i, col + i);
                if(board.getPiece(endPosition) == null) {
                    moves.add(new CMove(myPosition, endPosition, null));
                } else if(board.getPiece(endPosition).getTeamColor() != getTeamColor()) {
                    moves.add(new CMove(myPosition, endPosition, null));
                    break;
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        //DownLeft
        for(int i = 1; i <= 8; i++) {
            if(row - i >= 1 && col - i >= 1) {
                ChessPosition endPosition = new CPosition(row - i, col - i);
                if(board.getPiece(endPosition) == null) {
                    moves.add(new CMove(myPosition, endPosition, null));
                } else if(board.getPiece(endPosition).getTeamColor() != getTeamColor()) {
                    moves.add(new CMove(myPosition, endPosition, null));
                    break;
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        return moves;
    }
}
