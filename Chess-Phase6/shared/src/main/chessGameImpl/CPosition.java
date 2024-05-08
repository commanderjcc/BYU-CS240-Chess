package chessGameImpl;

import java.util.Objects;

/**
 * CPosition class implements the ChessPosition interface and represents a position on the chess board.
 */
public class CPosition implements chess.ChessPosition{

    /**
     * The row this position is in
     */
    private final int x;

    /**
     * The column this position is in
     */
    private final int y;

    /**
     * Constructor for CPosition class.
     * 
     * @param x  Which row this position is in
     * @param y  Which column this position is in
     */
    public CPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @param o Object to compare
     * @return true if options are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CPosition that = (CPosition) o;
        return x == that.x && y == that.y;
    }


    /**
     * @return a hash including its internals
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    @Override
    public int getRow() {
        return x;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    @Override
    public int getColumn() {
        return y;
    }
}
