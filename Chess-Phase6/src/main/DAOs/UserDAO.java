package DAOs;

import Models.User;
import dataAccess.DataAccessException;

import java.sql.SQLException;

/**
 * This class represents a Data Access Object (DAO) for User objects.
 * It provides methods for adding, retrieving, updating, and deleting User objects from a data source.
 */
public class UserDAO extends DAO {

    /**
     * Constructs a new UserDAO object.
     */
    public UserDAO() {
        super();
    }

    /**
     * Checks if a username exists in the data source.
     * @param user the user to check
     * @return true if the username exists, false otherwise
     * @throws DataAccessException if there is an error accessing the data source
     */
    public boolean userExists(User user) throws DataAccessException {
        var conn = db.getConnection();
        var sql = "SELECT * FROM chess.users WHERE username = ?;";
        try (var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            var rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            throw new DataAccessException("Error: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
    }

    /**
     * Adds a User object to the data source.
     *
     * @param user the User object to add
     * @throws DataAccessException if there is an error accessing the data source or the username is already taken
     */
    public void addUser(User user) throws DataAccessException {
        //Check if username already exists
        if (userExists(user)) {
            throw new DataAccessException("Error: Username already exists");
        }

        var conn = db.getConnection();
        var sql = "INSERT INTO chess.users (username, password, email) VALUES (?, ?, ?);";
        try (var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
    }

    /**
     * Retrieves a User object from the data source with the specified username.
     *
     * @param username the username of the User object to retrieve
     * @return the User object with the specified username, or null if no such User object exists
     * @throws DataAccessException if there is an error accessing the data source or the user does not exist
     */
    public User getUser(String username) throws DataAccessException {
        //Check if username exists
        if (!userExists(new User(username, null, null))) {
            throw new DataAccessException("Error: Username does not exist");
        }

        var conn = db.getConnection();
        var sql = "SELECT * FROM chess.users WHERE username = ?;";
        try (var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getString("username"), rs.getString("password"), rs.getString("email"));
            }
            return null;
        } catch (SQLException e) {
            throw new DataAccessException("Error: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
    }

    /**
     * Updates a User object in the data source.
     *
     * @param user the User object to update
     * @throws DataAccessException if there is an error accessing the data source or the user does not already exist
     */
    public void updateUser(User user) throws DataAccessException {
        //Check if username exists
        if (!userExists(user)) {
            throw new DataAccessException("Error: Username does not exist");
        }

        var conn = db.getConnection();
        var sql = "UPDATE chess.users SET password = ?, email = ? WHERE username = ?;";
        try (var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getPassword());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
    }

    /**
     * Clears all User objects from the data source.
     * @throws DataAccessException if there is an error accessing the data source
     */
    public void clear() throws DataAccessException {
        var conn = db.getConnection();
        var sql = "DELETE FROM chess.users;";
        try (var stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
    }

    /**
     * Deletes a User object from the data source.
     *
     * @param user the User object to delete
     * @throws DataAccessException if there is an error accessing the data source or the user does not exist
     */
    public void deleteUser(User user) throws DataAccessException {
        //Check if username exists
        if (!userExists(user)) {
            throw new DataAccessException("Error: Username does not exist");
        }

        var conn = db.getConnection();
        var sql = "DELETE FROM chess.users WHERE username = ?;";
        try (var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error: " + e.getMessage());
        } finally {
            db.closeConnection(conn);
        }
    }
}
