package ServerFacade;

import Exceptions.ResponseException;
import Models.AuthToken;
import Models.ModelDeserializer;
import RequestClasses.JoinGameRequest;
import ResultClasses.*;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * A facade for the server.
 */
public class ServerFacade {

    private final String serverUrl;

    /**
     * Creates a new ServerFacade.
     *
     * @param url The url of the server.
     */
    public ServerFacade(String url) {
        serverUrl = url;
    }

    /**
     * Clears the Database
     */
    public void clear() throws ResponseException {
        makeRequest("DELETE", "/db", null, ClearDBResult.class, null);
    }

    /**
     * Logs the user in.
     * @param username The username of the user.
     * @param password The password of the user.
     * @return a LoginResult object containing the auth token of the user.
     * @throws ResponseException if there is an error
     */
    public LoginResult login(String username, String password) throws ResponseException {
        var request = new RequestClasses.LoginRequest(username, password);
        return makeRequest("POST", "/session", request, LoginResult.class, null);
    }

    /**
     * Registers a new user.
     * @param username The username of the user.
     * @param password The password of the user.
     * @param email The email of the user.
     * @return a RegisterResult object containing the auth token of the user.
     * @throws ResponseException if there is an error
     */
    public RegisterResult register(String username, String password, String email) throws ResponseException {
        var request = new RequestClasses.RegisterRequest(username, password, email);
        return makeRequest("POST", "/user", request, RegisterResult.class, null);
    }

    /**
     * Logs the user out.
     *
     * @param authToken The auth token of the user.
     * @throws ResponseException if there is an error
     */
    public void logout(AuthToken authToken) throws ResponseException {
        makeRequest("DELETE", "/session", authToken, LogoutResult.class, authToken);
    }

    /**
     * Lists all games.
     * @param authToken The auth token of the user.
     * @return a ListGamesResult object containing the list of games.
     * @throws ResponseException if there is an error
     */
    public ListGamesResult listGames(AuthToken authToken) throws ResponseException {
        return makeRequest("GET", "/game", authToken, ListGamesResult.class, authToken);
    }

    /**
     * Creates a new game.
     * @param authToken The auth token of the user.
     * @param gameID The ID of the game.
     * @return a CreateGameResult object containing the game ID.
     * @throws ResponseException if there is an error
     */
    public JoinGameResult joinGame(AuthToken authToken, Integer gameID, String playerColor) throws ResponseException {
        var request = new JoinGameRequest(authToken, gameID, playerColor);
        return makeRequest("PUT", "/game", request, JoinGameResult.class, authToken);
    }

    /**
     * Creates a new game.
     * @param authToken The auth token of the user.
     * @param gameName The name of the game.
     * @return a CreateGameResult object containing the game ID.
     * @throws ResponseException if there is an error
     */
    public CreateGameResult createGame(AuthToken authToken, String gameName) throws ResponseException {
        var request = new RequestClasses.CreateGameRequest(authToken, gameName);
        return makeRequest("POST", "/game", request, CreateGameResult.class, authToken);
    }

    /**
     * Makes the request to the server and returns the response.
     * @param method The HTTP method to use.
     * @param path The path to the endpoint.
     * @param request The request object.
     * @param responseClass The class of the response object.
     * @param authToken The auth token of the user.
     * @param <T> The type of the response object.
     * @return The response object.
     * @throws ResponseException if there is an error
     */
    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass, AuthToken authToken) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            if (authToken != null) {
                http.setRequestProperty("Authorization", authToken.getAuthToken());
            }
            http.setDoOutput(true);

            if (!method.equals("GET")) {
                writeBody(request, http);
            }
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    /**
     * Writes the request body to the server.
     * @param request The request object.
     * @param http The HTTP connection.
     * @throws IOException if there is an error writing to the http object
     */
    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    /**
     * Throws an exception if the response is not successful.
     * @param http The HTTP connection.
     * @throws IOException if there is an error reading from the http object
     * @throws ResponseException if the response is not successful
     */
    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new ResponseException(status, "failure: " + status);
        }
    }

    /**
     * Reads the response body from the server.
     * @param http The HTTP connection.
     * @param responseClass The class of the response object.
     * @param <T> The type of the response object.
     * @return The response object.
     * @throws IOException if there is an error reading from the http object
     */
    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = ModelDeserializer.deserialize(reader, responseClass);
                }
            }
        }
        return response;
    }

    /**
     * Checks if the status code is successful.
     * @param status The status code.
     * @return true if the status code is successful, false otherwise
     */
    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}
