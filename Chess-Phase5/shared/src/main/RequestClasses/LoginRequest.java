package RequestClasses;

/**
 * Represents a login request from a user.
 */
public class LoginRequest extends Request {

    /**
     * The username of the user making the request.
     */
    private String username;

    /**
     * The password of the user making the request.
     */
    private String password;

    /**
     * Constructs a new LoginRequest object with null username and password.
     */
    public LoginRequest() {
        super();
        this.username = null;
        this.password = null;
    }

    /**
     * Constructs a new LoginRequest object with the given username and password.
     * @param username the username of the user making the request
     * @param password the password of the user making the request
     */
    public LoginRequest(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
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
}
