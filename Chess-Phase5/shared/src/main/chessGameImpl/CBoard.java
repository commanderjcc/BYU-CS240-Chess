package chessGameImpl;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

/**
 * CBoard class implements the ChessBoard interface and represents the chess board.
 * It contains methods to add, get, and replace chess pieces on the board, as well as reset the board to its default starting position.
 * The board is represented as a 2D ArrayList of ChessPiece objects.
 */
public class CBoard implements chess.ChessBoard {
    /**
     * The 2D ArrayList that represents the chess board.
     */
    private ArrayList<ArrayList<ChessPiece>> board = new ArrayList<>();

    /**
     * Constructor for the CBoard class. Initializes the board with a blank 8x8 grid of ChessPiece objects.
     */
    public CBoard() {
        for (int i = 0; i < 8; i++) {
            var blankrow = new ArrayList<ChessPiece>(8);
            for (int j = 0; j < 8; j++) {
                blankrow.add(null);
            }
            board.add(blankrow);
        }
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        var row = board.get(position.getRow()-1);
        row.remove(position.getColumn()-1);

        switch (piece.getPieceType()) {
            case PAWN -> piece = new Pawn(piece.getTeamColor());
            case ROOK -> piece = new Rook(piece.getTeamColor());
            case KNIGHT -> piece = new Knight(piece.getTeamColor());
            case BISHOP -> piece = new Bishop(piece.getTeamColor());
            case QUEEN -> piece = new Queen(piece.getTeamColor());
            case KING -> piece = new King(piece.getTeamColor());
        }

        row.add(position.getColumn()-1, piece);
    }

    /**
     * Overrides the equals method to compare two CBoard objects.
     * @param o the object to compare to this CBoard
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CBoard that = (CBoard) o;
        return Objects.equals(board, that.board);
    }

    /**
     * Returns the hash code value for this chess board.
     * The hash code is generated based on the current state of the board.
     *
     * @return the hash code value for this chess board
     */
    @Override
    public int hashCode() {
        return Objects.hash(board);
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return board.get(position.getRow()-1).get(position.getColumn()-1);
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    @Override
    public void resetBoard() {
        board = new ArrayList<>();

        //White Row 1
        var row = new ArrayList<ChessPiece>(8);
        row.add(new Rook(ChessGame.TeamColor.WHITE));
        row.add(new Knight(ChessGame.TeamColor.WHITE));
        row.add(new Bishop(ChessGame.TeamColor.WHITE));
        row.add(new Queen(ChessGame.TeamColor.WHITE));
        row.add(new King(ChessGame.TeamColor.WHITE));
        row.add(new Bishop(ChessGame.TeamColor.WHITE));
        row.add(new Knight(ChessGame.TeamColor.WHITE));
        row.add(new Rook(ChessGame.TeamColor.WHITE));
        board.add(row);

        //White Row 2
        row = new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            row.add(new Pawn(ChessGame.TeamColor.WHITE));
        }
        board.add(row);

        //Midfield
        for (int i = 2; i < 6; i++) {
            row = new ArrayList<>(8);
            for (int j = 0; j < 8; j++) {
                row.add(null);
            }
            board.add(row);
        }

        //Black Row 7
        row = new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            row.add(new Pawn(ChessGame.TeamColor.BLACK));
        }
        board.add(row);

        //Black Row 8
        row = new ArrayList<>(8);
        row.add(new Rook(ChessGame.TeamColor.BLACK));
        row.add(new Knight(ChessGame.TeamColor.BLACK));
        row.add(new Bishop(ChessGame.TeamColor.BLACK));
        row.add(new Queen(ChessGame.TeamColor.BLACK));
        row.add(new King(ChessGame.TeamColor.BLACK));
        row.add(new Bishop(ChessGame.TeamColor.BLACK));
        row.add(new Knight(ChessGame.TeamColor.BLACK));
        row.add(new Rook(ChessGame.TeamColor.BLACK));
        board.add(row);
    }

    /**
     * Replaces a piece on the board with another piece
     * 
     * @param startPosition  The location of the piece that is moving
     * @param endPosition  The location of the piece that will be captured
     */
    @Override
    public void replacePiece(ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType pieceType) {
        ChessPiece piece;
        if (pieceType == null) {
            piece = getPiece(startPosition);
        } else {
            switch (pieceType) {
                case PAWN -> piece = new Pawn(getPiece(startPosition).getTeamColor());
                case ROOK -> piece = new Rook(getPiece(startPosition).getTeamColor());
                case KNIGHT -> piece = new Knight(getPiece(startPosition).getTeamColor());
                case BISHOP -> piece = new Bishop(getPiece(startPosition).getTeamColor());
                case QUEEN -> piece = new Queen(getPiece(startPosition).getTeamColor());
                case KING -> piece = new King(getPiece(startPosition).getTeamColor());
                default -> throw new IllegalStateException("Unexpected value: " + pieceType);
            }
        }

        board.get(startPosition.getRow()-1).remove(startPosition.getColumn()-1);
        board.get(startPosition.getRow()-1).add(startPosition.getColumn()-1, null);
        board.get(endPosition.getRow()-1).remove(endPosition.getColumn()-1);
        board.get(endPosition.getRow()-1).add(endPosition.getColumn()-1, piece);
    }

    /**
     * Returns a deep copy of the current CBoard object.
     * 
     * @return a new CBoard object that is a copy of the current board
     */
    @Override
    public CBoard copy() {
        CBoard copiedBoard = new CBoard();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if (board.get(i).get(j) == null) {
                    continue;
                }
                ChessPiece piece = board.get(i).get(j).copy();

                copiedBoard.addPiece(new CPosition(i+1, j+1), piece);
            }
        }

        return copiedBoard;
    }

    /**
     * A custom deserializer for the ChessBoard interface.
     */
    public static class ChessBoardTA implements JsonDeserializer<ChessBoard> {

        /**
         * @param jsonElement the JsonElement being deserialized
         * @param type the type of the JsonElement to deserialize to
         * @param jsonDeserializationContext the context of the deserialization
         * @return a ChessBoard object
         * @throws JsonParseException if there is an error deserializing the JsonElement
         */
        @Override
        public ChessBoard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            var gson = new GsonBuilder()
                    .registerTypeAdapter(ChessPiece.class, new CPiece.ChessPieceTA())
                    .create();
            return gson.fromJson(jsonElement, CBoard.class);
        }
    }
}
