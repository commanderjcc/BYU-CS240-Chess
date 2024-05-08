package ResultClasses;

import Models.AuthToken;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

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
    public static class LoginResultTypeAdapter implements JsonSerializer<LoginResult> {

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
            return jsonObject;
        }
    }
}
