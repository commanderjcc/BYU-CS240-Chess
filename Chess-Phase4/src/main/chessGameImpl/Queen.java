package chessGameImpl;

import chess.*;

import java.util.Collection;
import java.util.HashSet;

/**
 * Implementation of a Queen ChessPiece
 */
public class Queen extends CPiece{
    /**
     * Constructor for Queen, with the option to set the type of piece
     * @param pieceColor The color of the Queen piece
     * @param type The type of the Queen piece
     */
    public Queen(ChessGame.TeamColor pieceColor, PieceType type) {
        super(pieceColor, type);
    }

    /**
     * Constructor for Queen
     * @param pieceColor The color of the Queen piece
     */
    public Queen(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.QUEEN);
    }

    /**
     * @return a Queen with the same color
     */
    @Override
    public ChessPiece copy() {
        return new Queen(getTeamColor());
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

