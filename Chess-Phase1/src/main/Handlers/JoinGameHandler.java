package Handlers;

import RequestClasses.JoinGameRequest;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

/**
 * Handler for the joinGame command
 */
public class JoinGameHandler extends Handler {

    private final Services.JoinGameService service;

    /**
     * @param service The service object that the handler will use to perform its task
     */
    public JoinGameHandler(Services.JoinGameService service) {
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
        RequestClasses.JoinGameRequest javaRequestObj = this.getRequestClass(request);
        // Call the service object and get the result object
        ResultClasses.JoinGameResult javaResultObj = service.joinGame(javaRequestObj);

        // Unpack the Result Object into a Spark Result
        response.status(javaResultObj.getStatus());
        return new Gson().toJson(javaResultObj);
    }

    /**
     * @param request The JSON string to be deserialized
     * @return The Java representation of the JSON string
     */
    @Override
    public RequestClasses.JoinGameRequest getRequestClass(Request request) {
        JoinGameRequest req;
        if (request.body() != null) {
            try {
                req = new Gson().fromJson(request.body(), RequestClasses.JoinGameRequest.class);
            } catch (Exception e) {
                req = new RequestClasses.JoinGameRequest();
            }
        } else {
            req = new RequestClasses.JoinGameRequest();
        }

        if (request.headers("Authorization") != null) {
            var token = new Models.AuthToken();
            token.setAuthToken(request.headers("Authorization"));
            req.setAuthToken(token);
        }
        return req;
    }
}
