package chessImpl;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class CPiece implements chess.ChessPiece{
    private ChessGame.TeamColor pieceColor;
    private PieceType type;

    public CPiece(ChessGame.TeamColor pieceColor, PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * @return Which team this chess piece belongs to
     */
    @Override
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    @Override
    public PieceType getPieceType() {
        return type;
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
        return new ArrayList<>();
    }

    /**
     * @return
     */
    @Override
    public ChessPiece copy() {
       return new CPiece(pieceColor, type);
    }
}
