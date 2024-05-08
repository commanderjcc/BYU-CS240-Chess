package DAOs;

import Models.Game;
import chess.ChessGame;
import chessGameImpl.CGame;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dataAccess.DataAccessException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * This class represents a Data Access Object (DAO) for Game objects.
 * It extends the DAO class and provides methods for adding, retrieving,
 * updating, and deleting Game objects from a data source.
 */
public class GameDAO extends DAO {
    /**
     * Constructs a new GameDAO object.
     */
    public GameDAO() {
        super();
    }

    /**
     * Check if the gameID exists in the db
     * @param game the Game to check
     * @return true if the gameID exists, false otherwise
     * @throws DataAccessException if there is an error accessing the data
     */
    public boolean gameExists(Game game) throws DataAccessException {
        var conn = db.getConnection();
        var sql = "SELECT * FROM chess.games WHERE gameID = ?;";
        try (var stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, game.getGameID());
            var rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            throw new DataAccessException("Error: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
    }

    /**
     * Adds a Game object to the DAO's HashMap.
     * @param game the Game object to add
     * @throws DataAccessException if there is an error accessing the data or the gameID already exists
     */
    public void addGame(Game game) throws DataAccessException {
        //Check if gameID already exists
        if (gameExists(game)) {
            throw new DataAccessException("Error: GameID already exists");
        }

        var conn = db.getConnection();
        var sql = "INSERT INTO chess.games (gameID, whiteUsername, blackUsername, observers, gameName, game) VALUES (?, ?, ?, ?, ?, ?);";
        try (var stmt = conn.prepareStatement(sql)) {
            var gson = new Gson();

            stmt.setInt(1, game.getGameID());
            stmt.setString(2, game.getWhiteUsername());
            stmt.setString(3, game.getBlackUsername());
            stmt.setString(4, gson.toJson(game.getObservers()));
            stmt.setString(5, game.getGameName());
            stmt.setString(6, CGame.toJSON(game.getGame()));
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new DataAccessException("Error: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
    }

    /**
     * Retrieves a Game object from the DAO's HashMap by name.
     * @param gameID the GameID of the Game object to retrieve
     * @return the Game object with the specified ID, or null if it does not exist
     * @throws DataAccessException if there is an error accessing the data or the game does not exist
     */
    public Game getGame(Integer gameID) throws DataAccessException {
        //Check if gameID exists
        if (!gameExists(new Game(gameID, null, null, new ArrayList<>(),null , null))) {
            throw new DataAccessException("Error: GameID does not exist");
        }

        var conn = db.getConnection();
        var sql = "SELECT * FROM chess.games WHERE gameID = ?;";
        try (var stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, gameID);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                var whiteUsername = rs.getString("whiteUsername");
                var blackUsername = rs.getString("blackUsername");
                var gameName = rs.getString("gameName");
                var observersString = rs.getString("observers");
                HashSet<String> observers = new Gson().fromJson(observersString, new TypeToken<HashSet<String>>() {}.getType());
                var gameJSON = rs.getString("game");
                var gson = new GsonBuilder()
                        .registerTypeAdapter(ChessGame.class, new CGame.ChessGameTA())
                        .create();
                var game = gson.fromJson(gameJSON, ChessGame.class);
                return new Game(gameID, whiteUsername, blackUsername, observers, gameName, game);
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
        return null;
    }

    /**
     * Retrieves all Game objects from the DAO's HashMap.
     * @return a HashSet containing all Game objects in the DAO's HashMap
     * @throws DataAccessException if there is an error accessing the data (there isn't right now since it's a HashMap)
     */
    public HashSet<Game> getAllGames() throws DataAccessException {
        var result = new HashSet<Game>();

        var conn = db.getConnection();
        var sql = "SELECT * FROM chess.games;";
        try (var stmt = conn.prepareStatement(sql)) {
            var rs = stmt.executeQuery();
            while (rs.next()) {
                var gameID = rs.getInt("gameID");
                var whiteUsername = rs.getString("whiteUsername");
                var blackUsername = rs.getString("blackUsername");
                var gameName = rs.getString("gameName");
                var observersString = rs.getString("observers");
                HashSet<String> observers = new Gson().fromJson(observersString, new TypeToken<HashSet<String>>() {}.getType());
                var gameJSON = rs.getString("game");
                var gson = new GsonBuilder().registerTypeAdapter(ChessGame.class, new CGame.ChessGameTA()).create();
                var game = gson.fromJson(gameJSON, ChessGame.class);

                result.add(new Game(gameID, whiteUsername, blackUsername, observers, gameName, game));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
        return result;
    }

    /**
     * Updates a Game object in the DAO's HashMap.
     *
     * @param game the Game object to update
     * @throws DataAccessException if there is an error accessing the data or the game does not already exist
     */
    public void updateGame(Game game) throws DataAccessException {
        //Check if gameID exists
        if (!gameExists(game)) {
            throw new DataAccessException("Error: GameID does not exist");
        }

        var conn = db.getConnection();
        var sql = "UPDATE chess.games SET whiteUsername = ?, blackUsername = ?, observers = ?, gameName = ?, game = ? WHERE gameID = ?;";
        try (var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, game.getWhiteUsername());
            stmt.setString(2, game.getBlackUsername());
            stmt.setString(3, new Gson().toJson(game.getObservers()));
            stmt.setString(4, game.getGameName());
            stmt.setString(5, CGame.toJSON(game.getGame()));
            stmt.setInt(6, game.getGameID());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new DataAccessException("Error: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }

    }

    /**
     * Claims a spot in a Game object in the DAO's HashMap.
     * @param gameID the GameID of the Game object to update
     * @param username the username of the Player object to add
     * @param color the color of the Player object to add
     * @throws DataAccessException if there is an error accessing the data or the game does not already exist
     */
    public void claimSpot(Integer gameID, String username, String color) throws DataAccessException {
        //Check that playerId is not null
        if (username == null) {
            throw new DataAccessException("Error: PlayerID is null");
        }

        //Check if gameID exists
        if (!gameExists(new Game(gameID, null, null, new ArrayList<>(),null, null))) {
            throw new DataAccessException("Error: GameID does not exist");
        }
        var game = getGame(gameID);
        if (color == null) {
            //Check if username is in observers
            if (game.isObserver(username)) {
                throw new DataAccessException("Error: Observer already exists");
            }
            game.addObserver(username);
        } else if (color.equals("WHITE")) {
            //Check if whitePlayerID is null
            if (game.getWhiteUsername() != null && !game.getWhiteUsername().equals(username)) {
                throw new DataAccessException("Error: White player already exists");
            }
            game.setWhiteUsername(username);
        }
        else if (color.equals("BLACK")) {
            //Check if blackPlayerID is null
            if (game.getBlackUsername() != null && !game.getBlackUsername().equals(username)) {
                throw new DataAccessException("Error: Black player already exists");
            }
            game.setBlackUsername(username);
        } else {
            throw new DataAccessException("Error: Invalid color");
        }

        updateGame(game);
    }

    /**
     * Clears all Game objects from the DAO's db
     * @throws DataAccessException if there is an error accessing the data
     */
    public void clear() throws DataAccessException {
        var conn = db.getConnection();
        var sql = "DELETE FROM chess.games;";
        try (var stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new DataAccessException("Error: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
    }

    /**
     * Deletes a Game object from the DAO's HashMap.
     * @param game the Game object to delete
     * @throws DataAccessException if there is an error accessing the data or the game does not exist
     */
    public void deleteGame(Game game) throws DataAccessException {
        //Check if gameID exists
        if (!gameExists(game)) {
            throw new DataAccessException("Error: GameID does not exist");
        }

        var conn = db.getConnection();
        var sql = "DELETE FROM chess.games WHERE gameID = ?;";
        try (var stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, game.getGameID());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new DataAccessException("Error: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
    }

}
