package ResultClasses;

import Models.Game;
import chessGameImpl.CGame;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents the result of a request to list all games in the system.
 */
public class ListGamesResult extends Result {

    /**
     * The collection of all games to include in the result.
     */
    private List<Game> games;

    /**
     * Creates a new instance of ListGamesResult with a null list of games.
     */
    public ListGamesResult() {
        super();
    }

    /**
     * Creates a new instance of a successful ListGamesResult with the specified list of games and a default status of 200.
     * @param games The list of games to include in the result.
     */
    public ListGamesResult(Collection<Game> games) {
        super(200);
        this.games = new ArrayList<>(games);
    }

    /**
     * Creates a new instance of ListGamesResult with the specified status and message, and a null list of games.
     * @param status The status code of the result.
     * @param message The message associated with the result.
     */
    public ListGamesResult(int status, String message) {
        super(status, message);
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(Collection<Game> games) {
        this.games = new ArrayList<>(games);
    }

    public static class ListGamesResultTypeAdapter implements JsonDeserializer<ListGamesResult> {

        /**
         * @param jsonElement The json to change into an object
         * @param type the type to deserialize into
         * @param jsonDeserializationContext the context of the JSON deserialization
         * @return A listGamesResult object
         * @throws JsonParseException if there is an error parsing the JSON
         */
        @Override
        public ListGamesResult deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            if (jsonObject.has("games")) {
                JsonArray jsonArray = jsonObject.getAsJsonArray("games");
                List<Game> games = new ArrayList<>();
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(CGame.class, new CGame.ChessGameTA());
                Gson gson = gsonBuilder.create();
                for (JsonElement element : jsonArray) {
                    games.add(gson.fromJson(element, Game.class));
                }

                ListGamesResult listGamesResult = new ListGamesResult(games);
            }

            ListGamesResult listGamesResult = new ListGamesResult();
            listGamesResult.setStatus(jsonObject.get("status").getAsInt());
            listGamesResult.setMessage(jsonObject.get("message").getAsString());
            listGamesResult.setGames(jsonDeserializationContext.deserialize(jsonObject.get("games"), List.class));
            return listGamesResult;
        }
    }
}
