package webSocketMessages.userCommands;

import Models.AuthToken;

import java.util.Objects;

/**
 * Represents a command a user can send the server over a websocket
 * 
 * Note: You can add to this class, but you should not alter the existing
 * methods.
 */
public class UserGameCommand {

    private AuthToken actualAuthToken;

    /**
     * Constructs a UserGameCommand object with the specified authentication token.
     *
     * @param authToken the authentication token
     */
    public UserGameCommand(AuthToken authToken) {
        this.authToken = authToken.getAuthToken();
        this.actualAuthToken = authToken;
    }

    /**
     * Sets the authentication token.
     *
     * @param trueAuthToken the true authentication token
     */
    public void setAuthToken(AuthToken trueAuthToken) {
        this.actualAuthToken = trueAuthToken;
    }

    /**
     * Represents the types of user game commands.
     */
    public enum CommandType {
        JOIN_PLAYER,
        JOIN_OBSERVER,
        MAKE_MOVE,
        LEAVE,
        RESIGN
    }

    protected CommandType commandType;

    private final String authToken;

    /**
     * Returns the authentication string.
     *
     * @return the authentication string
     */
    public String getAuthString() {
        return authToken;
    }

    /**
     * Returns the authentication token.
     *
     * @return the authentication token
     */
    public AuthToken getAuthToken() {
        return actualAuthToken;
    }

    /**
     * Returns the username associated with the authentication token.
     *
     * @return the username
     */
    public String getUsername() {
        return actualAuthToken.getUsername();
    }

    /**
     * Returns the command type.
     *
     * @return the command type
     */
    public CommandType getCommandType() {
        return this.commandType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UserGameCommand that))
            return false;
        return getCommandType() == that.getCommandType() && Objects.equals(getAuthString(), that.getAuthString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommandType(), getAuthString());
    }
}
