package Handlers;

import Services.LoginService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.Request;
import spark.Response;

/**
 * Handler for the login command
 */
public class LoginHandler extends Handler {

    private final LoginService service;

    /**
     * @param service The service object that the handler will use to perform its task
     */
    public LoginHandler(LoginService service) {
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
        RequestClasses.LoginRequest javaLoginRequestObj = this.getRequestClass(request);
        // Call the service object and get the result object
        ResultClasses.LoginResult javaLoginResultObj = service.login(javaLoginRequestObj);

        // Unpack the Result Object into a Spark Result
        response.status(javaLoginResultObj.getStatus());
        Gson gson = new GsonBuilder().registerTypeAdapter(ResultClasses.LoginResult.class, new ResultClasses.LoginResult.LoginResultTypeAdapter()).create();
        return gson.toJson(javaLoginResultObj);
    }

    /**
     * @param request The JSON string to be deserialized
     * @return The Java representation of the JSON string
     */
    @Override
    public RequestClasses.LoginRequest getRequestClass(Request request) {
        RequestClasses.LoginRequest req;
        if (request.body() == null) {
            req = new RequestClasses.LoginRequest();
        } else {
            try {
                req = new Gson().fromJson(request.body(), RequestClasses.LoginRequest.class);
            } catch (Exception e) {
                req = new RequestClasses.LoginRequest();
            }
            if (req == null) {
                req = new RequestClasses.LoginRequest();
            }
        }
        return req;
    }
}
