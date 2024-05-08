package chess;

import chessGameImpl.CPosition;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Represents a single square position on a chess board
 * 
 * Note: You can add to this interface, but you should not alter the existing
 * methods.
 */
public interface ChessPosition {
    /**
     * @return which row this position is in
     *         1 codes for the bottom row
     */
    int getRow();

    /**
     * @return which column this position is in
     *         1 codes for the left row
     */
    int getColumn();

    /**
     * Custom TypeAdapter for ChessPosition
     */
    class CPositionTA implements JsonDeserializer<CPosition> {

        /**
         * Deserializes a CPosition object from a JsonElement
         * @param jsonElement The JsonElement to deserialize
         * @param type The type of the JsonElement
         * @param jsonDeserializationContext The JsonDeserializationContext
         * @return The deserialized CPosition object
         * @throws JsonParseException If an error occurs while deserializing the JsonElement
         */
        @Override
        public CPosition deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return new Gson().fromJson(jsonElement, CPosition.class);
        }
    }
}
