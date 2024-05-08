package myTests;

import DAOs.*;
import Models.AuthToken;
import Models.Game;
import Models.User;
import chessGameImpl.CGame;
import dataAccess.DataAccessException;
import dataAccess.Database;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

public class DAOTests {
    // Database
    private Database db;

    // DAOs
    private AuthDAO authDAO;
    private GameDAO gameDAO;
    private UserDAO userDAO;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        db.clear();
        authDAO = new AuthDAO();
        gameDAO = new GameDAO();
        userDAO = new UserDAO();
    }

    @AfterAll
    public static void tearDown() throws DataAccessException {
        Database db = new Database();
        db.clear();
    }

    @Test
    public void testAuthDAOauthTokenExistsPositive() {
        // Make auth tokens
        var auth1 = new AuthToken("auth1", "username1");
        var auth2 = new AuthToken("auth2", "username2");

        // Test addAuthToken
        try {
            authDAO.addAuthToken(auth1);
            authDAO.addAuthToken(auth2);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test authTokenExists
        try {
            Assertions.assertTrue(authDAO.authTokenExists(auth1));
            Assertions.assertTrue(authDAO.authTokenExists(auth2));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testAuthDAOauthTokenExistsNegative() {
        // Make auth tokens
        var auth1 = new AuthToken("auth1", "username1");

        // Test authTokenExists
        try {
            Assertions.assertFalse(authDAO.authTokenExists(auth1));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testAuthDAOaddAuthTokenPositive() {
        // Make auth tokens
        var auth1 = new AuthToken("auth1", "username1");

        // Test addAuthToken
        try {
            authDAO.addAuthToken(auth1);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test authTokenExists
        try {
            Assertions.assertTrue(authDAO.authTokenExists(auth1));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testAuthDAOaddAuthTokenNegative() {
        // Make auth tokens
        var auth1 = new AuthToken("auth1", "username1");

        // Try adding twice
        try {
            authDAO.addAuthToken(auth1);
            Assertions.assertThrows(DataAccessException.class, () -> authDAO.addAuthToken(auth1));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testAuthDAOcheckAuthTokenPositive() {
        // Make auth tokens
        var auth1 = new AuthToken("auth1", "username1");

        // Test addAuthToken
        try {
            authDAO.addAuthToken(auth1);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test checkAuthToken
        try {
            Assertions.assertTrue(authDAO.checkAuthToken(auth1));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testAuthDAOcheckAuthTokenNegative() {
        // Make auth tokens
        var auth1 = new AuthToken("auth1", "username1");

        // Test checkAuthToken
        Assertions.assertThrows(DataAccessException.class, () -> authDAO.checkAuthToken(auth1));
    }

    @Test
    public void testAuthDAOgetUsernamePositive() {
        // Make auth tokens
        var auth1 = new AuthToken("auth1", "username1");

        // Test addAuthToken
        try {
            authDAO.addAuthToken(auth1);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test getUsername
        try {
            Assertions.assertEquals("username1", authDAO.getUsername(auth1));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testAuthDAOgetUsernameNegative() {
        // Make auth tokens
        var auth1 = new AuthToken("auth1", "username1");

        // Test getUsername
        Assertions.assertThrows(DataAccessException.class, () -> authDAO.getUsername(auth1));
    }

    @Test
    public void testAuthDAOclear() {
        // Make auth tokens
        var auth1 = new AuthToken("auth1", "username1");

        // Test addAuthToken
        try {
            authDAO.addAuthToken(auth1);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test clear
        try {
            authDAO.clear();
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test authTokenExists
        try {
            Assertions.assertFalse(authDAO.authTokenExists(auth1));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testAuthDAOdeleteAuthTokenPositive() {
        // Make auth tokens
        var auth1 = new AuthToken("auth1", "username1");

        // Test addAuthToken
        try {
            authDAO.addAuthToken(auth1);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test deleteAuthToken
        try {
            authDAO.deleteAuthToken(auth1);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test authTokenExists
        try {
            Assertions.assertFalse(authDAO.authTokenExists(auth1));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testAuthDAOdeleteAuthTokenNegative() {
        // Make auth tokens
        var auth1 = new AuthToken("auth1", "username1");

        // Test deleteAuthToken
        Assertions.assertThrows(DataAccessException.class, () -> authDAO.deleteAuthToken(auth1));
    }


    @Test
    public void testUserDAOuserExistsPositive() {
        // Make users
        var user1 = new User("username1", "password1", "email1");
        var user2 = new User("username2", "password2", "email2");

        // Test addUser
        try {
            userDAO.addUser(user1);
            userDAO.addUser(user2);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test userExists
        try {
            Assertions.assertTrue(userDAO.userExists(user1));
            Assertions.assertTrue(userDAO.userExists(user2));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testUserDAOuserExistsNegative() {
        // Make users
        var user1 = new User("username1", "password1", "email1");

        // Test userExists
        try {
            Assertions.assertFalse(userDAO.userExists(user1));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testUserDAOaddUserPositive() {
        // Make users
        var user1 = new User("username1", "password1", "email1");

        // Test addUser
        try {
            userDAO.addUser(user1);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test userExists
        try {
            Assertions.assertTrue(userDAO.userExists(user1));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testUserDAOaddUserNegative() {
        // Make users
        var user1 = new User("username1", "password1", "email1");

        // Try adding twice
        try {
            userDAO.addUser(user1);
            Assertions.assertThrows(DataAccessException.class, () -> userDAO.addUser(user1));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testUserDAOgetUserPositive() {
        // Make users
        var user1 = new User("username1", "password1", "email1");

        // Test addUser
        try {
            userDAO.addUser(user1);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test getUser
        try {
            Assertions.assertEquals(user1, userDAO.getUser(user1.getUsername()));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testUserDAOgetUserNegative() {
        // Make users
        var user1 = new User("username1", "password1", "email1");

        // Test getUser
        Assertions.assertThrows(DataAccessException.class, () -> userDAO.getUser(user1.getUsername()));
    }

    @Test
    public void testUserDAOupdateUserPositive() {
        //Make users
        var user1 = new User("username1", "password1", "email1");
        var user2 = new User("username1", "password2", "email2");

        // Test addUser
        try {
            userDAO.addUser(user1);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test updateUser
        try {
            userDAO.updateUser(user2);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test getUser
        try {
            Assertions.assertEquals(user2, userDAO.getUser(user2.getUsername()));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testUserDAOupdateUserNegative() {
        //Make users
        var user1 = new User("username1", "password1", "email1");

        // Test updateUser
        Assertions.assertThrows(DataAccessException.class, () -> userDAO.updateUser(user1));
    }

    @Test
    public void testUserDAOclear() {
        // Make users
        var user1 = new User("username1", "password1", "email1");

        // Test addUser
        try {
            userDAO.addUser(user1);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test clear
        try {
            userDAO.clear();
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test userExists
        try {
            Assertions.assertFalse(userDAO.userExists(user1));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testUserDAOdeleteUserPositive() {
        // Make users
        var user1 = new User("username1", "password1", "email1");

        // Test addUser
        try {
            userDAO.addUser(user1);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test deleteUser
        try {
            userDAO.deleteUser(user1);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test userExists
        try {
            Assertions.assertFalse(userDAO.userExists(user1));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testUserDAOdeleteUserNegative() {
        // Make users
        var user1 = new User("username1", "password1", "email1");

        // Test deleteUser
        Assertions.assertThrows(DataAccessException.class, () -> userDAO.deleteUser(user1));
    }

    @Test
    public void testGameDAOgameExistsPositive() {
        // Make users
        var user1 = new User("username1", "password1", "email1");
        var user2 = new User("username2", "password2", "email2");

        // Make games
        var cgame1 = new CGame();
        var cgame2 = new CGame();

        var game1 = new Game(1, user1.getUsername(), user2.getUsername(), new ArrayList<>(), "test", cgame1);
        var game2 = new Game(2, null, null, new ArrayList<>(), "test", cgame2);

        // Test addGame
        try {
            gameDAO.addGame(game1);
            gameDAO.addGame(game2);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test gameExists
        try {
            Assertions.assertTrue(gameDAO.gameExists(game1));
            Assertions.assertTrue(gameDAO.gameExists(game2));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testGameDAOgameExistsNegative() {
        // Make users
        var user1 = new User("username1", "password1", "email1");

        // Make games
        var cgame1 = new CGame();

        var game1 = new Game(1, user1.getUsername(), null, new ArrayList<>(), "test", cgame1);

        // Test gameExists
        try {
            Assertions.assertFalse(gameDAO.gameExists(game1));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testGameDAOaddGamePositive() {
        // Make users
        var user1 = new User("username1", "password1", "email1");

        // Make games
        var cgame1 = new CGame();

        var game1 = new Game(1, user1.getUsername(), null, new ArrayList<>(), "test", cgame1);

        // Test addGame
        try {
            gameDAO.addGame(game1);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test gameExists
        try {
            Assertions.assertTrue(gameDAO.gameExists(game1));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testGameDAOaddGameNegative() {
        // Make users
        var user1 = new User("username1", "password1", "email1");

        // Make games
        var cgame1 = new CGame();

        var game1 = new Game(1, user1.getUsername(), null, new ArrayList<>(), "test", cgame1);

        // Try adding twice
        try {
            gameDAO.addGame(game1);
            Assertions.assertThrows(DataAccessException.class, () -> gameDAO.addGame(game1));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testGameDAOgetGamePositive() {
        // Make users
        var user1 = new User("username1", "password1", "email1");

        // Make games
        var cgame1 = new CGame();

        var game1 = new Game(1, user1.getUsername(), null, new ArrayList<>(), "test", cgame1);

        // Test addGame
        try {
            gameDAO.addGame(game1);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test getGame
        try {
            Assertions.assertEquals(game1, gameDAO.getGame(game1.getGameID()));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testGameDAOgetGameNegative() {
        // Make games
        var cgame1 = new CGame();

        var game1 = new Game(1, null, null, new ArrayList<>(), "test", cgame1);

        // Test getGame
        Assertions.assertThrows(DataAccessException.class, () -> gameDAO.getGame(game1.getGameID()));
    }

    @Test
    public void testGameDAOgetAllGamesPositive() {
        // Make users
        var user1 = new User("username1", "password1", "email1");
        var user2 = new User("username2", "password2", "email2");

        // Make games
        var cgame1 = new CGame();
        var cgame2 = new CGame();

        var game1 = new Game(1, user1.getUsername(), user2.getUsername(), new ArrayList<>(), "test", cgame1);
        var game2 = new Game(2, null, null, new ArrayList<>(), "test", cgame2);

        // Test addGame
        try {
            gameDAO.addGame(game1);
            gameDAO.addGame(game2);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test getAllGames
        try {
            Assertions.assertArrayEquals(new Game[]{game1, game2}, gameDAO.getAllGames().toArray());
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testGameDAOgetAllGamesNegative() {
        // Test getAllGames
        try {
            Assertions.assertArrayEquals(new Game[]{}, gameDAO.getAllGames().toArray());
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testGameDAOupdateGamePositive() {
        // Make users
        var user1 = new User("username1", "password1", "email1");
        var user2 = new User("username2", "password2", "email2");

        // Make games
        var cgame1 = new CGame();
        var cgame2 = new CGame();

        var game1 = new Game(1, user1.getUsername(), user2.getUsername(), new ArrayList<>(), "test", cgame1);
        var game2 = new Game(1, null, null, new ArrayList<>(), "test", cgame2);

        // Test addGame
        try {
            gameDAO.addGame(game1);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test updateGame
        try {
            gameDAO.updateGame(game2);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test getGame
        try {
            Assertions.assertEquals(game2, gameDAO.getGame(game2.getGameID()));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testGameDAOupdateGameNegative() {
        // Make games
        var cgame1 = new CGame();

        var game1 = new Game(1, null, null, new ArrayList<>(), "test", cgame1);

        // Test updateGame
        Assertions.assertThrows(DataAccessException.class, () -> gameDAO.updateGame(game1));
    }

    @Test
    public void testGameDAOclaimSpotPositive() {
        // Make users
        var user1 = new User("username1", "password1", "email1");
        var user2 = new User("username2", "password2", "email2");

        // Make games
        var cgame1 = new CGame();
        var cgame2 = new CGame();

        var game1 = new Game(1, null, null, new ArrayList<>(), "test", cgame1);
        var game2 = new Game(2, null, null, new ArrayList<>(), "test", cgame2);

        // Test addGame
        try {
            gameDAO.addGame(game1);
            gameDAO.addGame(game2);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test claimSpot
        try {
            gameDAO.claimSpot(game1.getGameID(), user1.getUsername(), "WHITE");
            gameDAO.claimSpot(game2.getGameID(), user2.getUsername(), "BLACK");
            gameDAO.claimSpot(game2.getGameID(), user1.getUsername(), null);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test getGame
        try {
            Assertions.assertEquals(user1.getUsername(), gameDAO.getGame(game1.getGameID()).getWhiteUsername());
            Assertions.assertEquals(user2.getUsername(), gameDAO.getGame(game2.getGameID()).getBlackUsername());
            Assertions.assertArrayEquals(new String[]{user1.getUsername()}, gameDAO.getGame(game2.getGameID()).getObservers().toArray(new String[0]));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testGameDAOclaimSpotNegative() {
        // Make users
        var user1 = new User("username1", "password1", "email1");

        // Make games
        var cgame1 = new CGame();

        var game1 = new Game(1, null, null, new ArrayList<>(), "test", cgame1);

        // Test claimSpot
        Assertions.assertThrows(DataAccessException.class, () -> gameDAO.claimSpot(game1.getGameID(), user1.getUsername(), "WHITE"));
    }

    @Test
    public void testGameDAOclear() {
        // Make users
        var user1 = new User("username1", "password1", "email1");

        // Make games
        var cgame1 = new CGame();

        var game1 = new Game(1, user1.getUsername(), null, new ArrayList<>(), "test", cgame1);

        // Test addGame
        try {
            gameDAO.addGame(game1);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test clear
        try {
            gameDAO.clear();
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test gameExists
        try {
            Assertions.assertFalse(gameDAO.gameExists(game1));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testGameDAOdeleteGamePositive() {
        // Make users
        var user1 = new User("username1", "password1", "email1");

        // Make games
        var cgame1 = new CGame();

        var game1 = new Game(1, user1.getUsername(), null, new ArrayList<>(), "test", cgame1);

        // Test addGame
        try {
            gameDAO.addGame(game1);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test deleteGame
        try {
            gameDAO.deleteGame(game1);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test gameExists
        try {
            Assertions.assertFalse(gameDAO.gameExists(game1));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testGameDAOdeleteGameNegative() {
        // Make games
        var cgame1 = new CGame();

        var game1 = new Game(1, null, null, new ArrayList<>(), "test", cgame1);

        // Test deleteGame
        Assertions.assertThrows(DataAccessException.class, () -> gameDAO.deleteGame(game1));
    }

    @Test
    public void testAuthDAOPositive() {
        // Make auth tokens
        AuthToken auth1 = new AuthToken("auth1", "username1");
        AuthToken auth2 = new AuthToken("auth2", "username2");

        // Test addAuth
        try {
            authDAO.addAuthToken(auth1);
            authDAO.addAuthToken(auth2);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test getAuth
        try {
            Assertions.assertTrue(authDAO.checkAuthToken(auth1));
            Assertions.assertTrue(authDAO.checkAuthToken(auth2));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test removeAuth
        try {
            authDAO.deleteAuthToken(auth1);
            authDAO.deleteAuthToken(auth2);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test getAuth
        try {
            Assertions.assertFalse(authDAO.authTokenExists(auth1));
            Assertions.assertFalse(authDAO.authTokenExists(auth2));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test clear
        try {
            authDAO.addAuthToken(auth1);
            authDAO.addAuthToken(auth2);
            authDAO.clear();
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test getAuth
        try {
            Assertions.assertFalse(authDAO.authTokenExists(auth1));
            Assertions.assertFalse(authDAO.authTokenExists(auth2));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testAuthDAONegative() {
        // Make auth tokens
        AuthToken auth1 = new AuthToken("auth1", "username1");

        // Test getAuth
        try {
            Assertions.assertFalse(authDAO.authTokenExists(auth1));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test removeAuth
        Assertions.assertThrows(DataAccessException.class, () -> authDAO.deleteAuthToken(auth1));

        // Add two
        try {
            authDAO.addAuthToken(auth1);
            Assertions.assertThrows(DataAccessException.class, () -> authDAO.addAuthToken(auth1));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testUserDAOPositive() {
        // Make users
        User user1 = new User("username1", "password1", "email1");
        User user2 = new User("username2", "password2", "email2");

        // Test addUser
        try {
            userDAO.addUser(user1);
            userDAO.addUser(user2);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test getUser
        try {
            Assertions.assertEquals(user1, userDAO.getUser(user1.getUsername()));
            Assertions.assertEquals(user2, userDAO.getUser(user2.getUsername()));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test removeUser
        try {
            userDAO.deleteUser(user1);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test getUser
        Assertions.assertThrows(DataAccessException.class, () -> userDAO.getUser(user1.getUsername()));

        // Test clear
        try {
            userDAO.clear();
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test getUser
        Assertions.assertThrows(DataAccessException.class, () -> userDAO.getUser(user2.getUsername()));
    }

    @Test
    public void testUserDAONegative() {
        // Make users
        User user1 = new User("username1", "password1", "email1");

        // Test getUser
        Assertions.assertThrows(DataAccessException.class, () -> userDAO.getUser(user1.getUsername()));

        // Test removeUser
        Assertions.assertThrows(DataAccessException.class, () -> userDAO.deleteUser(user1));

        // Add two
        try {
            userDAO.addUser(user1);
            Assertions.assertThrows(DataAccessException.class, () -> userDAO.addUser(user1));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testGameDAOPositive() {
        // Make users
        User user1 = new User("username1", "password1", "email1");
        User user2 = new User("username2", "password2", "email2");

        // Make games
        CGame cgame1 = new CGame();
        CGame cgame2 = new CGame();

        Game game1 = new Game(1, user1.getUsername(), user2.getUsername(), new ArrayList<>(), "test", cgame1);
        Game game2 = new Game(2, null, null, new ArrayList<>(), "test", cgame2);

        // Test addGame
        try {
            gameDAO.addGame(game1);
            gameDAO.addGame(game2);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test getGame
        try {
            Assertions.assertEquals(game1, gameDAO.getGame(game1.getGameID()));
            Assertions.assertEquals(game2, gameDAO.getGame(game2.getGameID()));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test getGameList
        try {
            Assertions.assertArrayEquals(new Game[]{game1, game2}, gameDAO.getAllGames().toArray());
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test removeGame
        try {
            gameDAO.deleteGame(game1);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test getGame
        Assertions.assertThrows(DataAccessException.class, () -> gameDAO.getGame(game1.getGameID()));

        // Test ClaimSpot
        try {
            gameDAO.claimSpot(game2.getGameID(), "username1", "BLACK");
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test clear
        try {
            gameDAO.clear();
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        // Test getGame
        Assertions.assertThrows(DataAccessException.class, () -> gameDAO.getGame(game2.getGameID()));
    }

}
