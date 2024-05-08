package chessGameImpl;

import chess.*;

import java.util.Collection;
import java.util.HashSet;

/**
 * Represents a Bishop chess piece
 */
public class Bishop extends CPiece{

    /**
     * Constructor for Bishop
     * @param pieceColor The color of the Bishop piece
     */
    public Bishop(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.BISHOP);
    }

    /**
     * Constructor for Bishop, allows you to set the type of piece
     * @param pieceColor The color of the Bishop piece
     * @param type The type of the Bishop piece
     */
    public Bishop(ChessGame.TeamColor pieceColor, PieceType type) {
        super(pieceColor, type);
    }

    /**
     * Creates a copy of the Bishop piece
     * @return A copied Bishop piece
     */
    @Override
    public ChessPiece copy() {
        return new Bishop(getTeamColor());
    }

    /**
     * Calculates all the positions a Bishop piece can move to
     * Does not take into account moves that are illegal due to leaving the king in danger
     * @param board The current state of the board
     * @param myPosition The location of the Bishop piece to generate a moveset for
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
