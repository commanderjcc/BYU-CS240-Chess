package Models;

import java.util.Objects;

/**
 * Represents a user in the system.
 * A user has a username, password, and email.
 */
public class User {

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * The email of the user.
     */
    private String email;

    /**
     * Constructs a new User object with null values for username, password, and email.
     */
    public User() {
        this.username = null;
        this.password = null;
        this.email = null;
    }

    /**
     * Constructs a new User object with the specified username, password, and email.
     * @param username the username of the user
     * @param password the password of the user
     * @param email the email of the user
     */
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    /**
     * Checks if the specified User object is equal to this User object.
     * @param o the User object to compare
     * @return true if the User objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(email, user.email);
    }

    /**
     * Generates a hash code for this User object.
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, password, email);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
