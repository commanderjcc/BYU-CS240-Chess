package chessImpl;

import java.util.Objects;

public class CPosition implements chess.ChessPosition{
    private int x;
    private int y;

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
