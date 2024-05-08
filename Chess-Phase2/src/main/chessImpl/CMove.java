package chessImpl;

import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Objects;

public class CMove implements chess.ChessMove{
    private ChessPosition start;
    private ChessPosition end;
    private ChessPiece.PieceType promotion;

    public CMove(ChessPosition start, ChessPosition end, ChessPiece.PieceType promotion) {
        this.start = start;
        this.end = end;
        this.promotion = promotion;
    }

    /**
     * @return ChessPosition of starting location
     */
    @Override
    public ChessPosition getStartPosition() {
        return start;
    }

    /**
     * @return ChessPosition of ending location
     */
    @Override
    public ChessPosition getEndPosition() {
        return end;
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    @Override
    public ChessPiece.PieceType getPromotionPiece() {
        return promotion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CMove cMove = (CMove) o;
        return Objects.equals(start, cMove.start) && Objects.equals(end, cMove.end) && promotion == cMove.promotion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, promotion);
    }
}
