package chessGameImpl;

import chess.*;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Implementation of the ChessPiece interface representing a chess piece
 */
public class CPiece implements chess.ChessPiece{

    /**
     * The color of the piece
     */
    private final ChessGame.TeamColor pieceColor;

    /**
     * The type of the piece (pawn, rook, etc.)
     */
    private final PieceType type;

    /**
     * Constructor for CPiece
     * @param pieceColor The color of the piece
     * @param type The type of the piece
     */
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
     * @param board  The current state of the board
     * @param myPosition  the location of the piece to generate a moveset for
     * @return Collection of valid moves
     */
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return new ArrayList<>();
    }

    /**
     * @return a copy of the ChessPiece
     */
    @Override
    public ChessPiece copy() {
       return new CPiece(pieceColor, type);
    }

    public static class ChessPieceTA implements JsonDeserializer<ChessPiece> {

        /**
         * @param jsonElement the JsonElement being deserialized
         * @param type the type of the JsonElement to deserialize to
         * @param jsonDeserializationContext the context of the deserialization
         * @return a ChessPiece object
         * @throws JsonParseException if there is an error deserializing the JsonElement
         */
        @Override
        public ChessPiece deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
           return new Gson().fromJson(jsonElement, CPiece.class);
        }
    }
}
