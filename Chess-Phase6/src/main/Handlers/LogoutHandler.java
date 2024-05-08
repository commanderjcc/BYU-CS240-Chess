package Handlers;

import Services.LogoutService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

/**
 * Handler for the logout command
 */
public class LogoutHandler extends Handler {

    private final LogoutService service;

    /**
     * @param service The service object that the handler will use to perform its task
     */
    public LogoutHandler(LogoutService service) {
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
        RequestClasses.LogoutRequest javaLogoutRequestObj = this.getRequestClass(request);
        // Call the service object and get the result object
        ResultClasses.LogoutResult javaLogoutResultObj = service.logout(javaLogoutRequestObj);

        // Unpack the Result Object into a Spark Result
        response.status(javaLogoutResultObj.getStatus());

        return new Gson().toJson(javaLogoutResultObj);
    }

    /**
     * @param request The JSON string to be deserialized
     * @return The Java representation of the JSON string
     */
    @Override
    public RequestClasses.LogoutRequest getRequestClass(Request request) {
        var req = new RequestClasses.LogoutRequest();
        if (request.headers("Authorization") != null) {
            var token = new Models.AuthToken();
            token.setAuthToken(request.headers("Authorization"));
            req.setAuthToken(token);
        }
        return req;
    }
}
