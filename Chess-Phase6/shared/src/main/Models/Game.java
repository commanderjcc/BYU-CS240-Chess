package Models;

import chess.ChessGame;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Represents a game of chess.
 */
public class Game {

    /**
     * The ID of the game.
     */
    private Integer gameID;

    /**
     * The username of the player playing as white.
     */
    private String whiteUsername;

    /**
     * The username of the player playing as black.
     */
    private String blackUsername;

    /**
     * The usernames of the observers.
     */
    private HashSet<String> observers;

    /**
     * The display name of the game.
     */
    private String gameName;

    /**
     * The ChessGame object representing the game.
     */
    private ChessGame game;

    /**
     * Constructs a new Game object with null values.
     */
    public Game() {
        this.gameID = null;
        this.whiteUsername = null;
        this.blackUsername = null;
        this.observers = new HashSet<>();
        this.gameName = null;
        this.game = null;
    }

    /**
     * Constructs a new Game object with the specified values.
     *
     * @param gameID the ID of the game
     * @param whiteUsername the username of the player playing as white
     * @param blackUsername the username of the player playing as black
     * @param observers the usernames of the observers
     * @param gameName the name of the game
     * @param game the ChessGame object representing the game
     */
    public Game(Integer gameID, String whiteUsername, String blackUsername, Collection<String> observers, String gameName, ChessGame game) {
        this.gameID = gameID;
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
        this.observers = new HashSet<>(observers);
        this.gameName = gameName;
        this.game = game;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }

    public String getWhiteUsername() {
        return whiteUsername;
    }

    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }

    public String getBlackUsername() {
        return blackUsername;
    }

    public void setBlackUsername(String blackUsername) {
        this.blackUsername = blackUsername;
    }

    public HashSet<String> getObservers() {
        return observers;
    }

    /**
     * Adds the given username to the list of observers.
     * @param observer the username to add
     */
    public void addObserver(String observer) {
        this.observers.add(observer);
    }

    /**
     * Checks if the given username is an observer of this game.
     * @param observer the username to check
     * @return true if the username is an observer, false otherwise
     */
    public boolean isObserver(String observer) {
        return this.observers.contains(observer);
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public ChessGame getGame() {
        return game;
    }

    public void setGame(ChessGame game) {
        this.game = game;
    }

    /**
     * Generates a new game ID.
     *
     * @return the new game ID
     */
    public int generateGameID() {
        // Generate a random 10 digit number
        return (int) (Math.random() * 1000000000L);
    }

    @Override
    public String toString() {
        return "ID: " + gameID + ", Name:'" + gameName + '\'' +
                ", White:'" + whiteUsername + '\'' +
                ", Black:'" + blackUsername + '\'';
    }

    /**
     * games are equal if IDs are equal
     * @param o the object to compare to
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(gameID, game.gameID);
    }

    /**
     * Hashing by ID should be sufficient
     */
    @Override
    public int hashCode() {
        return Objects.hash(gameID);
    }

    /**
     * Checks if the game is over.
     * @return true if the game is over, false otherwise
     */
    public boolean isOver() {
        return getGame().getTeamTurn() == ChessGame.TeamColor.FINISHED;
    }

    /**
     * Sets the game to be over.
     */
    public void setOver() {
        getGame().setTeamTurn(ChessGame.TeamColor.FINISHED);
    }
}
