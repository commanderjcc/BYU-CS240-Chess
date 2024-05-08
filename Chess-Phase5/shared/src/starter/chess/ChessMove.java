package chess;

import chessGameImpl.CMove;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Represents moving a chess piece on a chessboard
 * 
 * Note: You can add to this interface, but you should not alter the existing
 * methods.
 */
public interface ChessMove {
    /**
     * @return ChessPosition of starting location
     */
    ChessPosition getStartPosition();

    /**
     * @return ChessPosition of ending location
     */
    ChessPosition getEndPosition();

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     * 
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    ChessPiece.PieceType getPromotionPiece();

    /**
     * Custom TypeAdapter for ChessMove
     */
    class ChessMoveTA implements JsonDeserializer<ChessMove> {

        /**
         * Deserializes a ChessMove object from a JsonElement
         * @param jsonElement The JsonElement to deserialize
         * @param type The type of the JsonElement
         * @param jsonDeserializationContext The JsonDeserializationContext
         * @return The deserialized ChessMove object
         * @throws JsonParseException If an error occurs while deserializing the JsonElement
         */
        @Override
        public ChessMove deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            var gson = new GsonBuilder().registerTypeAdapter(ChessPosition.class, new ChessPosition.CPositionTA()).create();
            return gson.fromJson(jsonElement, CMove.class);
        }
    }
}
