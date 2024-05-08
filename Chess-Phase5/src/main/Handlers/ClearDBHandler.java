package Handlers;

import RequestClasses.ClearDBRequest;
import ResultClasses.ClearDBResult;
import Services.ClearDBService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

/**
 * Handler for the clear command
 */
public class ClearDBHandler extends Handler {
    private final ClearDBService service;

    /**
     * @param service The service object that the handler will use to perform its task
     */
    public ClearDBHandler(ClearDBService service) {
        this.service = service;
    }

    /**
     * @param request A spark request object, if there is a body, it'll be an auth token
     * @return A ClearDBRequest object
     */
    public ClearDBRequest getRequestClass(Request request) {
        ClearDBRequest req;
        if (request.body() == null) {
            req = new ClearDBRequest();
        } else {
            try {
                req = new Gson().fromJson(request.body(), ClearDBRequest.class);
            } catch (Exception e) {
                req = new ClearDBRequest();
            }
            if (req == null) {
                req = new ClearDBRequest();
            }
        }
        return req;
    }

    /**
     * @param request  The HTTP request object
     * @param response The HTTP response object
     * @return The object (usually a JSON object) to be returned to the client
     */
    @Override
    public Object handle(Request request, Response response) {
        // Get the request object
        ClearDBRequest javaClearDBRequestObj = this.getRequestClass(request);
        // Call the service object and get the result object
        ClearDBResult javaClearDBResultObj = service.clearDB();

        // Unpack the Result Object into a Spark Result
        response.status(javaClearDBResultObj.getStatus());

        return new Gson().toJson(javaClearDBResultObj);
    }
}
