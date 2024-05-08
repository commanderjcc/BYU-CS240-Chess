package Models;

import java.util.Objects;
import java.util.UUID;

/**
 * This class represents an authentication token for a user.
 * It contains the user's authentication token and username.
 */
public class AuthToken {

    /**
     * The authentication token as a random string.
     */
    private String authToken;

    /**
     * The username of the user this authentication token corresponds to.
     */
    private String username;

    /**
     * Constructs a new AuthToken object with null values.
     */
    public AuthToken() {
        this.authToken = null;
        this.username = null;
    }

    /**
     * Constructs a new AuthToken object with the given username.
     *
     * @param username the username
     */
    public AuthToken(String username) {
        this.authToken = UUID.randomUUID().toString();
        this.username = username;
    }

    /**
     * Constructs a new AuthToken object with these values.
     *
     * @param authToken the authentication token
     * @param username the username
     */
    public AuthToken(String authToken, String username) {
        this.authToken = authToken;
        this.username = username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Determines if this AuthToken object is equal to another object.
     * Compares only on the string of the AuthToken. User is not considered.
     *
     * @param o the other object
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken1 = (AuthToken) o;
        return Objects.equals(authToken, authToken1.authToken);
    }


    /**
     * Generates a hash code for this AuthToken object, solely based on the authToken.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(authToken);
    }
}
