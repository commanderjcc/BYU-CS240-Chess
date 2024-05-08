package chess;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * 
 * Note: You can add to this interface, but you should not alter the existing
 * methods.
 */
public interface ChessBoard {

    /**
     * Adds a chess piece to the chessboard
     * 
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    void addPiece(ChessPosition position, ChessPiece piece);

    /**
     * Gets a chess piece on the chessboard
     * 
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     *         position
     */
    ChessPiece getPiece(ChessPosition position);

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    void resetBoard();

    /**
     * Replaces a piece on the board with another piece during a capture
     * 
     * @param startPosition The position to move the piece from
     * @param endPosition   The position to move the piece to
     */
    void replacePiece(ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType pieceType);

    /**
     * Copies the chessboard
     * 
     * @return A deep copy of the chessboard
     */
    ChessBoard copy();
}
