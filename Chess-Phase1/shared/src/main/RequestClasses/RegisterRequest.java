package RequestClasses;

/**
 * Represents a request to register a new user.
 */
public class RegisterRequest extends Request {

    /**
     * The username of the new user.
     */
    private String username;

    /**
     * The password of the new user.
     */
    private String password;

    /**
     * The email of the new user.
     */
    private String email;

    /**
     * Constructs an empty RegisterRequest object.
     */
    public RegisterRequest() {
        super();
        this.username = null;
        this.password = null;
        this.email = null;
    }

    /**
     * Constructs a RegisterRequest object with the specified username, password, and email.
     * @param username the username of the new user
     * @param password the password of the new user
     * @param email the email of the new user
     */
    public RegisterRequest(String username, String password, String email) {
        super();
        this.username = username;
        this.password = password;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
