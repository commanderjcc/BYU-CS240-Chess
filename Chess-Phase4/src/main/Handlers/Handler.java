package Handlers;

import spark.Request;
import spark.Response;
import spark.Route;

/**
 * The base class for all handlers
 */
public abstract class Handler implements Route {
    /**
     * @param request The HTTP request object
     * @param response The HTTP response object
     * @return The object (usually a JSON object) to be returned to the client
     */
    public abstract Object handle(Request request, Response response);

    /**
     * @param request The JSON string to be deserialized
     * @return The Java representation of the JSON string
     */
    public abstract RequestClasses.Request getRequestClass(Request request);
}
