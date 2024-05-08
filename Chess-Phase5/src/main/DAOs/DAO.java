package DAOs;

import dataAccess.Database;

/**
 * This class represents a Data Access Object (DAO). In the future, it may include methods for interacting with the database
 */
public class DAO {
    protected final Database db;

    /**
     * Constructs a new DAO object.
     */
    public DAO() {
        db = new Database();
    }
}
