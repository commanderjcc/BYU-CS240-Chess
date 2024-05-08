package DAOs;

import Models.AuthToken;
import dataAccess.DataAccessException;

import java.sql.SQLException;

/**
 * This class represents a Data Access Object (DAO) for authentication tokens.
 * It extends the DAO class and provides methods for adding, checking, and deleting
 * authentication tokens. It also has a method for clearing all authentication tokens.
 */
public class AuthDAO extends DAO {

    /**
     * This is the constructor for the AuthDAO class.
     * It calls the constructor of its parent class, DAO, which currently does nothing.
     */
    public AuthDAO() {
        super();
    }

    /**
     * Check if the authToken exists in the database.
     * @param authToken the authToken to check
     * @return true if the authToken exists, false otherwise
     * @throws DataAccessException if there is an error accessing the database
     */
    public boolean authTokenExists(AuthToken authToken) throws DataAccessException {
        var conn = db.getConnection();
        var sql = "SELECT * FROM chess.auth WHERE authToken = ?;";
        try (var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken.getAuthToken());
            var rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new DataAccessException("authError: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
    }

    /**
     * Adds a new authentication token to the database.
     *
     * @param authToken the authentication token to add
     * @throws DataAccessException if there is an error accessing the database or the authentication token already exists
     */
    public void addAuthToken(AuthToken authToken) throws DataAccessException {
        //Check if authToken already exists
        if (authTokenExists(authToken)) {
            throw new DataAccessException("Error: Authentication token already exists");
        }

        var conn = db.getConnection();
        var sql = "INSERT INTO chess.auth (authToken, username) VALUES (?, ?);";
        try (var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken.getAuthToken());
            stmt.setString(2, authToken.getUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("authError: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
    }

    /**
     * Checks if an authentication token is in the database.
     *
     * @param authToken the authentication token to check
     * @return true if the authentication token is in the database, false otherwise
     * @throws DataAccessException if there is an error accessing the database
     */
    public boolean checkAuthToken(AuthToken authToken) throws DataAccessException {
        if (!authTokenExists(authToken)) {
            throw new DataAccessException("Error: Authentication token does not exist");
        }
        return true;
    }

    /**
     * Returns the username associated with an authentication token.
     *
     * @param authToken the authentication token to check
     * @throws DataAccessException if there is an error accessing the database or the authentication token does not exist
     */
    public String getUsername(AuthToken authToken) throws DataAccessException {
        if (!authTokenExists(authToken)) {
            throw new DataAccessException("Error: Authentication token does not exist");
        }

        var conn = db.getConnection();
        var sql = "SELECT username FROM chess.auth WHERE authToken = ?;";
        try (var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken.getAuthToken());
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("username");
            }
        } catch (SQLException e) {
            throw new DataAccessException("authError: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
        return null;
    }

    /**
     * Clears all authentication tokens from the database.
     * @throws DataAccessException if there is an error accessing the database
     */
    public void clear() throws DataAccessException {
        var conn = db.getConnection();
        var sql = "DELETE FROM chess.auth;";
        try (var stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
    }

    /**
     * Deletes an authentication token from the database.
     *
     * @param authToken the authentication token to delete
     * @throws DataAccessException if there is an error accessing the database or the authentication token does not exist
     */
    public void deleteAuthToken(AuthToken authToken) throws DataAccessException {
        //Check if authToken exists
        if (!authTokenExists(authToken)) {
            throw new DataAccessException("Error: Authentication token does not exist");
        }

        var conn = db.getConnection();
        var sql = "DELETE FROM chess.auth WHERE authToken = ?;";
        try (var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken.getAuthToken());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("authError: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
    }
}
