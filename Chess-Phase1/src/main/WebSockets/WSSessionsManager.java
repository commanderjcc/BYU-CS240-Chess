package WebSockets;

import com.google.gson.Gson;
import webSocketMessages.serverMessages.ServerMessage;

import org.eclipse.jetty.websocket.api.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class represents the WebSocket sessions manager.
 * It is responsible for managing which sessions are connected to which games.
 */
public class WSSessionsManager {
    private final Map<Integer, Map<String, Session>> sessions;

    /**
     * Constructs a new WSSessionsManager with the map as a HashMap.
     */
    public WSSessionsManager() {
        sessions = new HashMap<>();
    }

    /**
     * Adds a session to the sessions manager. Uses a TreeMap to store the sessions.
     *
     * @param gameId  The ID of the game the session is connected to.
     * @param username The username of the session.
     * @param session The session to add.
     */
    public void addSession(int gameId, String username, Session session) {
        if (!sessions.containsKey(gameId)) {
            sessions.put(gameId, new TreeMap<>());
        }
        sessions.get(gameId).put(username, session);
    }

    /**
     * Removes a session from the sessions manager.
     *
     * @param gameId  The ID of the game the session is connected to.
     * @param username The username of the session.
     */
    public void removeSession(int gameId, String username) {
        if (sessions.containsKey(gameId)) {
            sessions.get(gameId).remove(username);
        }
    }

    /**
     * Gets the session associated with the specified game ID.
     *
     * @param gameId The ID of the game.
     * @param username The username of the session.
     * @return The session associated with the game ID.
     */
    public Session getSession(int gameId, String username) {
        if (sessions.containsKey(gameId)) {
            return sessions.get(gameId).get(username);
        }
        return null;
    }

    /**
     * Gets the sessions associated with the specified game ID.
     *
     * @param gameId The ID of the game.
     * @return The sessions associated with the game ID.
     */
    public Map<String, Session> getSessions(int gameId) {
        if (sessions.containsKey(gameId)) {
            return sessions.get(gameId);
        }
        return null;
    }

    /**
     * Broadcasts a message to all sessions associated with the specified game ID.
     *
     * @param gameId The ID of the game.
     * @param message The message to broadcast.
     * @param usernameToExclude The username of the session to exclude from the broadcast.
     * @throws IOException If an I/O error occurs while sending the message.
     */
    public void broadcast(int gameId, ServerMessage message, String usernameToExclude) throws IOException {
        if (sessions.containsKey(gameId)) {
            for (Map.Entry<String, Session> entry : sessions.get(gameId).entrySet()) {
                if (!entry.getKey().equals(usernameToExclude)) {
                    entry.getValue().getRemote().sendString(new Gson().toJson(message));
                }
            }
        }
    }

    /**
     * Sends a message to the session associated with the specified game ID and username.
     *
     * @param gameId The ID of the game.
     * @param message The message to send.
     * @param username The username of the session.
     * @throws IOException If an I/O error occurs while sending the message.
     */
    public void sendMessage(int gameId, ServerMessage message, String username) throws IOException {
        if (sessions.containsKey(gameId)) {
            Session session = sessions.get(gameId).get(username);
            if (session != null) {
                session.getRemote().sendString(new Gson().toJson(message));
            }
        }
    }
}


