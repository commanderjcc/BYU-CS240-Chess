package chessGameImpl;

import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Objects;

/**
 * This class represents a chess move in the game of chess.
 */
public class CMove implements chess.ChessMove{
    /**
     * The starting position of the chess move.
     */
    private final ChessPosition start;

    /**
     * The ending position of the chess move.
     */
    private final ChessPosition end;

    /**
     * The type of piece to promote a pawn to if pawn promotion is part of this
     * chess move.
     */
    private final ChessPiece.PieceType promotion;

    /**
     * Constructor for CMove class.
     *
     * @param start     ChessPosition of starting location
     * @param end       ChessPosition of ending location
     * @param promotion Type of piece to promote a pawn to, or null if no promotion
     */
    public CMove(ChessPosition start, ChessPosition end, ChessPiece.PieceType promotion) {
        this.start = start;
        this.end = end;
        this.promotion = promotion;
    }

    /**
     * Gets the starting position of the chess move.
     *
     * @return ChessPosition of starting location
     */
    @Override
    public ChessPosition getStartPosition() {
        return start;
    }

    /**
     * Gets the ending position of the chess move.
     *
     * @return ChessPosition of ending location
     */
    @Override
    public ChessPosition getEndPosition() {
        return end;
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move.
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    @Override
    public ChessPiece.PieceType getPromotionPiece() {
        return promotion;
    }

    /**
     * Checks if two CMove objects are equal.
     *
     * @param o Object to compare to
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CMove cMove = (CMove) o;
        return Objects.equals(start, cMove.start) && Objects.equals(end, cMove.end) && promotion == cMove.promotion;
    }

    /**
     * Generates a hash code for the CMove object.
     *
     * @return hash code for the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(start, end, promotion);
    }
}
