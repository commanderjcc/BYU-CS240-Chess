package ResultClasses;

import Models.AuthToken;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Represents a result from a login request.
 * Inherits from the Result class and includes an AuthToken object.
 */
public class LoginResult extends Result {

    /**
     * The authentication token to be used for the user's session.
     */
    private AuthToken authToken;

    /**
     * Constructs a new LoginResult object with no authentication token.
     */
    public LoginResult() {
        super();
        this.authToken = null;
    }

    /**
     * Constructs a new LoginResult object with the given authentication token and a success status.
     * @param authToken the authentication token to use for the result
     */
    public LoginResult(AuthToken authToken) {
        super(200);
        this.authToken = authToken;
    }

    /**
     * Constructs a new LoginResult object with the given status and message.
     * @param status the status code of the result
     * @param message the message to include with the result
     */
    public LoginResult(int status, String message) {
        super(status, message);
        this.authToken = null;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    /**
     * This class is used to serialize a LoginResult object to JSON.
     */
    public static class LoginResultTypeAdapter implements JsonSerializer<LoginResult>, JsonDeserializer<LoginResult> {

        /**
         * @param loginResult The object to change into json
         * @param type The type of the object
         * @param jsonSerializationContext the context of the json serialization
         * @return a JSON object that represents the LoginResult
         */
        @Override
        public JsonElement serialize(LoginResult loginResult, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject jsonObject = new JsonObject();
            if (loginResult.getAuthToken() != null) {
                jsonObject.addProperty("authToken", loginResult.getAuthToken().getAuthToken());
                jsonObject.addProperty("username", loginResult.getAuthToken().getUsername());
            }
            if (loginResult.getMessage() != null) {
                jsonObject.addProperty("message", loginResult.getMessage());
            }
            jsonObject.addProperty("status", loginResult.getStatus());
            return jsonObject;
        }

        /**
        * Deserializes a LoginResult object from JSON.
        * @param json the JSON to deserialize
        * @param typeOfT the type of the Object to deserialize to
        * @param context the deserialization context
        * @return the deserialized LoginResult object
        * @throws JsonParseException if the JSON is not in the expected format
        */
        @Override
        public LoginResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            String authToken = null;
            if (jsonObject.has("authToken")) {
                authToken = jsonObject.get("authToken").getAsString();
            }

            String username = null;
            if (jsonObject.has("username")) {
                username = jsonObject.get("username").getAsString();
            }

            int status = 0;
            if (jsonObject.has("status")) {
                status = jsonObject.get("status").getAsInt();
            }

            String message = null;
            if (jsonObject.has("message")) {
                message = jsonObject.get("message").getAsString();
            }

            if (authToken != null && username != null) {
                return new LoginResult(new AuthToken(authToken, username));
            } else {
                return new LoginResult(status, message);
            }
        }
    }
}
