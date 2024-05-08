package Handlers;

import RequestClasses.CreateGameRequest;
import Services.CreateGameService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

/**
 * The handler class for the create game command
 */
public class CreateGameHandler extends Handler {

    private final CreateGameService service;

    /**
     * @param service The service object that the handler will use to perform its task
     */
    public CreateGameHandler(CreateGameService service) {
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
        RequestClasses.CreateGameRequest javaCreateGameRequestObj = this.getRequestClass(request);
        // Call the service object and get the result object
        ResultClasses.CreateGameResult javaCreateGameResultObj = service.createGame(javaCreateGameRequestObj);

        // Unpack the Result Object into a Spark Result
        response.status(javaCreateGameResultObj.getStatus());

        return new Gson().toJson(javaCreateGameResultObj);
    }

    /**
     * @param request The JSON string to be deserialized
     * @return The Java representation of the JSON string
     */
    @Override
    public RequestClasses.CreateGameRequest getRequestClass(Request request) {
        CreateGameRequest req = null;
        if (request.body() != null) {
            try {
                req = new com.google.gson.Gson().fromJson(request.body(), RequestClasses.CreateGameRequest.class);
            } catch (Exception e) {
                req = new CreateGameRequest();
            }
        }
        if (req == null) {
            req = new CreateGameRequest();
        }
        if (request.headers("Authorization") != null) {
            var token = new Models.AuthToken();
            token.setAuthToken(request.headers("Authorization"));
            req.setAuthToken(token);
        }

        return req;
    }
}
