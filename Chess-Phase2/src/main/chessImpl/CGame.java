package chessImpl;

import chess.*;

import java.util.Collection;
import java.util.HashSet;

public class CGame implements chess.ChessGame{
    private TeamColor teamColor = TeamColor.BLACK;
    private ChessBoard board = new CBoard();

    public CGame() {
        board.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    @Override
    public TeamColor getTeamTurn() {
        return teamColor;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    @Override
    public void setTeamTurn(TeamColor team) {
        teamColor = team;
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    @Override
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = board.getPiece(startPosition);
        if (piece == null) {
            return null;
        }

        var gameTeamColor = this.teamColor;
        this.teamColor = board.getPiece(startPosition).getTeamColor();
        Collection<ChessMove> moves = piece.pieceMoves(board, startPosition);

        HashSet<ChessMove> invalidMoves = new HashSet<>();
        for(ChessMove move : moves){
            ChessBoard tempBoard = board.copy();
            tempBoard.replacePiece(move.getStartPosition(), move.getEndPosition(), move.getPromotionPiece());
            ChessGame tempGame = new CGame();
            tempGame.setBoard(tempBoard);
            if(tempGame.isInCheck(teamColor)){
                invalidMoves.add(move);
            }
        }

        moves.removeAll(invalidMoves);

        this.teamColor = gameTeamColor;
        return moves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece piece = board.getPiece(move.getStartPosition());
        if (piece == null) {
            throw new InvalidMoveException("No piece found");
        }
        if (piece.getTeamColor() != teamColor) {
            throw new InvalidMoveException("Wrong turn");
        }
        if (!validMoves(move.getStartPosition()).contains(move)) {
            throw new InvalidMoveException("Invalid move");
        }
        board.replacePiece(move.getStartPosition(), move.getEndPosition(), move.getPromotionPiece());
        teamColor = teamColor == TeamColor.BLACK ? TeamColor.WHITE : TeamColor.BLACK;
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    @Override
    public boolean isInCheck(TeamColor teamColor) {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8 ; j++) {
                ChessPosition position = new CPosition(i, j);
                ChessPiece piece = board.getPiece(position);
                if (piece != null && piece.getTeamColor() != teamColor) {
                    Collection<ChessMove> moves = piece.pieceMoves(board, position);
                    for (ChessMove move : moves) {
                        if (board.getPiece(move.getEndPosition()) != null &&
                                board.getPiece(move.getEndPosition()).getPieceType() == ChessPiece.PieceType.KING &&
                                board.getPiece(move.getEndPosition()).getTeamColor() == teamColor) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        var possibleMoves = new HashSet<ChessMove>();
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8 ; j++) {
                ChessPosition position = new CPosition(i, j);
                ChessPiece piece = board.getPiece(position);
                if (piece != null && piece.getTeamColor() == teamColor) {
                    var possibleValidMoves = piece.pieceMoves(board, position);
                    if(possibleValidMoves != null) {
                        possibleMoves.addAll(possibleValidMoves);
                    }
                }
            }
        }

        for (ChessMove move : possibleMoves) {
            ChessBoard tempBoard = board.copy();
            tempBoard.replacePiece(move.getStartPosition(), move.getEndPosition(), move.getPromotionPiece());
            ChessGame tempGame = new CGame();
            tempGame.setBoard(tempBoard);
            if(!tempGame.isInCheck(teamColor)){
                return false;
            }
        }

        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        var gameTeamColor = this.teamColor;
        this.teamColor = teamColor;
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8 ; j++) {
                ChessPosition position = new CPosition(i, j);
                ChessPiece piece = board.getPiece(position);
                if (piece != null && piece.getTeamColor() == teamColor) {
                    var possibleValidMoves = validMoves(position);
                    if(possibleValidMoves != null) {
                        if (!possibleValidMoves.isEmpty()) {
                            return false;
                        }
                    }
                }
            }
        }

        this.teamColor = gameTeamColor;
        return true;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    @Override
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    @Override
    public ChessBoard getBoard() {
        return board;
    }
}
