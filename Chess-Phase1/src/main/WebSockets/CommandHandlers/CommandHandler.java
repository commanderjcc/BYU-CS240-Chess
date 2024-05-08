package WebSockets.CommandHandlers;

import Services.GameService;
import WebSockets.WSSessionsManager;
import com.google.gson.Gson;

/**
 * The CommandHandler class is responsible for handling commands received from WebSocket clients.
 * It manages WebSocket sessions and interacts with the GameService to process the commands.
 */
public class CommandHandler {
    protected final WSSessionsManager sessionsManager;

    protected final GameService gameService;

    protected final Gson gson = new Gson();

    /**
     * Constructs a new CommandHandler with the specified WSSessionsManager and GameService.
     * 
     * @param sessionsManager The WSSessionsManager to manage WebSocket sessions.
     * @param gameService The GameService to interact with for processing commands.
     */
    public CommandHandler(WSSessionsManager sessionsManager, GameService gameService) {
        this.sessionsManager = sessionsManager;
        this.gameService = gameService;
    }
}
