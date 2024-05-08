package Handlers;

import RequestClasses.RegisterRequest;
import ResultClasses.RegisterResult;
import Services.RegisterService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.Request;
import spark.Response;


/**
 * Handler for the register command
 */
public class RegisterHandler extends Handler {

    private final RegisterService service;

    /**
     * @param service The service object that the handler will use to perform its task
     */
    public RegisterHandler(RegisterService service) {
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
        RegisterRequest javaRegisterRequestObj = this.getRequestClass(request);
        // Call the service object and get the result object
        RegisterResult javaRegisterResultObj = service.register(javaRegisterRequestObj);

        // Unpack the Result Object into a Spark Result
        response.status(javaRegisterResultObj.getStatus());

        Gson gson = new GsonBuilder().registerTypeAdapter(RegisterResult.class, new RegisterResult.RegisterResultTypeAdapter()).create();
        return gson.toJson(javaRegisterResultObj);
    }

    /**
     * @param request The JSON string to be deserialized
     * @return The Java representation of the JSON string
     */
    @Override
    public RegisterRequest getRequestClass(Request request) {
        RegisterRequest req;
        if (request.body() == null) {
            req = new RegisterRequest();
        } else {
            try {
                req = new Gson().fromJson(request.body(), RegisterRequest.class);
            } catch (Exception e) {
                req = new RegisterRequest();
            }
            if (req == null) {
                req = new RegisterRequest();
            }
        }
        return req;
    }
}
