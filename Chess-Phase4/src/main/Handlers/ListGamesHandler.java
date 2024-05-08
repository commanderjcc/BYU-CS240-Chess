package Handlers;

import RequestClasses.ListGamesRequest;
import Services.ListGamesService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

/**
 * Handler for the listGames command
 */
public class ListGamesHandler extends Handler {

    private final ListGamesService service;

    /**
     * Creates a new ListGamesHandler object with the given service object
     *
     * @param service The service object that the handler will use to perform its task
     */
    public ListGamesHandler(ListGamesService service) {
        this.service = service;
    }

    /**
     * @param request  The HTTP request object
     * @param response The HTTP response object
     * @return The object (usually a JSON object) to be returned to the client
     */
    @Override
    public Object handle(Request request, Response response) {
        // Get the request object
        RequestClasses.ListGamesRequest javaRequestObj = this.getRequestClass(request);
        // Call the service object and get the result object
        ResultClasses.ListGamesResult javaResultObj = service.listGames(javaRequestObj);

        // Unpack the Result Object into a Spark Result
        response.status(javaResultObj.getStatus());
        return new Gson().toJson(javaResultObj);
    }

    /**
     * @param request The JSON string to be deserialized
     * @return The Java representation of the JSON string
     */
    @Override
    public RequestClasses.ListGamesRequest getRequestClass(Request request) {
        if (request.headers("Authorization") != null) {
            var token = new Models.AuthToken();
            token.setAuthToken(request.headers("Authorization"));
            return new ListGamesRequest(token);
        }
        return new ListGamesRequest();
    }
}
