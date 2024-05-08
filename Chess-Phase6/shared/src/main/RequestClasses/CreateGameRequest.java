package RequestClasses;

import Models.AuthToken;

/**
 * Represents a request to create a new game.
 * Inherits from the Request class.
 */
public class CreateGameRequest extends Request {

    /**
     * The name of the game.
     */
    private String gameName;

    /**
     * Constructs a new CreateGameRequest object with no authentication token.
     */
    public CreateGameRequest() {
        super();
        this.gameName = null;
    }

    /**
     * Constructs a new CreateGameRequest object with the given authentication token.
     * @param authToken the authentication token to use for the request
     */
    public CreateGameRequest(AuthToken authToken, String gameName) {
        super(authToken);
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
}
