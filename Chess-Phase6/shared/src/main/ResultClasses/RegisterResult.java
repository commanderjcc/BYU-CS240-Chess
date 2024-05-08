package ResultClasses;

import Models.AuthToken;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Represents the result of a user registration attempt.
 */
public class RegisterResult extends Result {

    /**
     * The authentication token generated for the registered user.
     */
    private AuthToken authToken;

    /**
     * The username of the registered user.
     */
    private String username;

    /**
     * Creates a new RegisterResult object with null values for authToken and username.
     */
    public RegisterResult() {
        super();
        this.authToken = null;
        this.username = null;
    }

    /**
     * Creates a new RegisterResult object with the given authToken and username.
     * @param authToken The authentication token generated for the registered user.
     * @param username The username of the registered user.
     */
    public RegisterResult(AuthToken authToken, String username) {
        super(200);
        this.authToken = authToken;
        this.username = username;
    }

    /**
     * Creates a new RegisterResult object with the given status and message, and null values for authToken and username.
     * @param status The HTTP status code of the registration attempt.
     * @param message A message describing the result of the registration attempt.
     */
    public RegisterResult(int status, String message) {
        super(status, message);
        this.authToken = null;
        this.username = null;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * A type adapter for the RegisterResult class.
     */
    public static class RegisterResultTypeAdapter implements JsonSerializer<RegisterResult>, JsonDeserializer<RegisterResult> {

        /**
         * Converts a RegisterResult object into a JsonElement for serialization.
         *
         * @param registerResult  The RegisterResult object to convert.
         * @param type The type of the object.
         * @param jsonSerializationContext The context in which the serialization is happening.
         * @return A JsonElement representing the RegisterResult object.
         */
        @Override
        public JsonElement serialize(RegisterResult registerResult, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject jsonObject = new JsonObject();
            if (registerResult.getAuthToken() != null) {
                jsonObject.addProperty("authToken", registerResult.getAuthToken().getAuthToken());
            }
            if (registerResult.getUsername() != null) {
                jsonObject.addProperty("username", registerResult.getUsername());
            }
            if (registerResult.getMessage() != null) {
                jsonObject.addProperty("message", registerResult.getMessage());
            }
            if (registerResult.getStatus() != 0) {
                jsonObject.addProperty("status", registerResult.getStatus());
            }
            return jsonObject;
        }

        /**
         * Converts a JsonElement into a RegisterResult object.
         *
         * @param jsonElement The JsonElement to convert.
         * @param type The type of the object.
         * @param jsonDeserializationContext The context in which the deserialization is happening.
         * @return A RegisterResult object representing the JsonElement.
         * @throws JsonParseException if the JsonElement cannot be converted to a RegisterResult object.
         */
        @Override
        public RegisterResult deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            String authToken = null;
            if (jsonObject.has("authToken")) {
                authToken = jsonObject.get("authToken").getAsString();
            }

            String username = null;
            if (jsonObject.has("username")) {
                username = jsonObject.get("username").getAsString();
            }

            String message = null;
            if (jsonObject.has("message")) {
                message = jsonObject.get("message").getAsString();
            }

            if (authToken != null && username != null) {
                return new RegisterResult(new AuthToken(authToken, username), username);
            } else {
                return new RegisterResult(400, message);
            }
        }
    }
}
