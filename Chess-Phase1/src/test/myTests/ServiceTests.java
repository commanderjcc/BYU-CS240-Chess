package myTests;

import DAOs.*;
import Handlers.*;
import Models.AuthToken;
import Models.Game;
import Models.User;
import RequestClasses.RegisterRequest;
import Services.*;
import chessGameImpl.CGame;
import dataAccess.DataAccessException;
import dataAccess.Database;
import org.junit.jupiter.api.*;
import Server.Server;

import java.util.ArrayList;

public class ServiceTests{
    // Database
    private Database db;

    // Server
    private Server server;

    // DAOs
    private AuthDAO authDAO;
    private GameDAO gameDAO;
    private UserDAO userDAO;

    // Services
    private LoginService loginService;
    private ClearDBService clearDBService;
    private RegisterService registerService;
    private CreateGameService createGameService;
    private JoinGameService joinGameService;
    private ListGamesService listGamesService;
    private LogoutService logoutService;

    // Handlers
    private LoginHandler loginHandler;
    private LogoutHandler logoutHandler;
    private ClearDBHandler clearDBHandler;
    private RegisterHandler registerHandler;
    private CreateGameHandler createGameHandler;
    private JoinGameHandler joinGameHandler;
    private ListGamesHandler listGamesHandler;


    @BeforeEach
    public void setUp() {
        //Init databases
        this.db = new Database();
        try {
            this.db.clear();
        } catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail("Exception thrown");
        }

        //Init DAOs
        this.authDAO = new AuthDAO();
        this.gameDAO = new GameDAO();
        this.userDAO = new UserDAO();

        //Init services
        this.loginService = new LoginService(this.authDAO, this.userDAO);
        this.clearDBService = new ClearDBService(this.authDAO, this.gameDAO, this.userDAO);
        this.createGameService = new CreateGameService(this.authDAO, this.gameDAO, this.userDAO);
        this.joinGameService = new JoinGameService(this.authDAO, this.gameDAO, this.userDAO);
        this.listGamesService = new ListGamesService(this.authDAO, this.gameDAO, this.userDAO);
        this.registerService = new RegisterService(this.authDAO, this.userDAO);
        this.logoutService = new LogoutService(this.authDAO, this.userDAO);

        //Init Handlers
        this.loginHandler = new LoginHandler(this.loginService);
        this.clearDBHandler = new ClearDBHandler(this.clearDBService);
        this.registerHandler = new RegisterHandler(this.registerService);
        this.createGameHandler = new CreateGameHandler(this.createGameService);
        this.joinGameHandler = new JoinGameHandler(this.joinGameService);
        this.listGamesHandler = new ListGamesHandler(this.listGamesService);
        this.logoutHandler = new LogoutHandler(this.logoutService);

        // Server
        this.server = new Server();
    }

    @Test
    public void testClearDBService_positive(){
        // Add a token to the database
        var temp = new AuthToken("test", "test");
        try {
            this.authDAO.addAuthToken(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add a user to the database
        var tempUser = new User("test", "test", "test");
        try {
            this.userDAO.addUser(tempUser);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add a game to the database
        var tempGame = new Models.Game(123, "test", "test", new ArrayList<>(), "test", new CGame());
        try {
            this.gameDAO.addGame(tempGame);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Clear the database
        var tempRequest = new RequestClasses.ClearDBRequest();
        var tempResult = this.clearDBService.clearDB();

        // Check that tempResult is correct
        Assertions.assertEquals(200, tempResult.getStatus());

        // Check that the database is empty
        Assertions.assertThrows(DataAccessException.class, () -> this.authDAO.checkAuthToken(temp));
        Assertions.assertThrows(DataAccessException.class, () -> this.userDAO.getUser(tempUser.getUsername()));
        Assertions.assertThrows(DataAccessException.class, () -> this.gameDAO.getGame(tempGame.getGameID()));
    }

    @Test
    public void testRegisterServicePositive() {
        // Register a user
        var tempRequest = new RequestClasses.RegisterRequest("test", "test", "test");
        var tempResult = this.registerService.register(tempRequest);

        // Check that tempResult is correct
        Assertions.assertEquals(200, tempResult.getStatus());
        Assertions.assertEquals("test", tempResult.getUsername());
        Assertions.assertNotNull(tempResult.getAuthToken());

        // Check that the user was added to the database
        try {
            var user = this.userDAO.getUser("test");
            Assertions.assertNotNull(user);
            Assertions.assertEquals("test", user.getUsername());
            Assertions.assertEquals("test", user.getPassword());
            Assertions.assertEquals("test", user.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Exception thrown");
        }

        // Check that the auth token was added to the database
        try {
            var exists = this.authDAO.checkAuthToken(tempResult.getAuthToken());
            Assertions.assertTrue(exists);
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Exception thrown");
        }
    }

    @Test
    public void testRegisterServiceNegative(){
        // Register a user
        var tempRequest = new RequestClasses.RegisterRequest("test", "test", "test");
        var tempResult = this.registerService.register(tempRequest);

        // Check that tempResult is correct
        Assertions.assertEquals(200, tempResult.getStatus());
        Assertions.assertEquals("test", tempResult.getUsername());
        Assertions.assertNotNull(tempResult.getAuthToken());

        // Check that the user was added to the database
        try {
            var user = this.userDAO.getUser("test");
            Assertions.assertNotNull(user);
            Assertions.assertEquals("test", user.getUsername());
            Assertions.assertEquals("test", user.getPassword());
            Assertions.assertEquals("test", user.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Exception thrown");
        }

        // Try adding the same user again
        tempRequest = new RequestClasses.RegisterRequest("test", "test", "test");
        tempResult = this.registerService.register(tempRequest);

        // Check that tempResult is correct
        Assertions.assertEquals(403, tempResult.getStatus());
        Assertions.assertEquals("Error: already taken", tempResult.getMessage());
        Assertions.assertNull(tempResult.getUsername());
        Assertions.assertNull(tempResult.getAuthToken());
    }

    @Test
    public void testLoginServicePositive(){
        // Register a user
        var tempRequest = new RequestClasses.RegisterRequest("test", "test", "test");
        var tempResult = this.registerService.register(tempRequest);

        // Check that tempResult is correct
        Assertions.assertEquals(200, tempResult.getStatus());
        Assertions.assertEquals("test", tempResult.getUsername());
        Assertions.assertNotNull(tempResult.getAuthToken());

        // Check that the user was added to the database
        try {
            var user = this.userDAO.getUser("test");
            Assertions.assertNotNull(user);
            Assertions.assertEquals("test", user.getUsername());
            Assertions.assertEquals("test", user.getPassword());
            Assertions.assertEquals("test", user.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Exception thrown");
        }

        // Login the user
        var loginRequest = new RequestClasses.LoginRequest("test", "test");
        var loginResult = this.loginService.login(loginRequest);

        // Check that tempResult is correct
        Assertions.assertEquals(200, loginResult.getStatus());
        Assertions.assertNotNull(loginResult.getAuthToken());

        // Check that the auth token was added to the database
        try {
            var exists = this.authDAO.checkAuthToken(loginResult.getAuthToken());
            Assertions.assertTrue(exists);
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Exception thrown");
        }
    }

    @Test
    public void testLoginServiceNegative() {
        // Register a user
        var tempRequest = new RequestClasses.RegisterRequest("test", "test", "test");
        var tempResult = this.registerService.register(tempRequest);

        // Check that the user was added to the database
        try {
            var user = this.userDAO.getUser("test");
            Assertions.assertNotNull(user);
            Assertions.assertEquals("test", user.getUsername());
            Assertions.assertEquals("test", user.getPassword());
            Assertions.assertEquals("test", user.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Exception thrown");
        }

        // Login the user
        var loginRequest = new RequestClasses.LoginRequest("test", "wrong");
        var loginResult = this.loginService.login(loginRequest);

        // Check that loginResult is correct
        Assertions.assertEquals(401, loginResult.getStatus());
        Assertions.assertNull(loginResult.getAuthToken());
    }

    @Test
    public void testLogoutServicePositive() {
        // Register a user
        var tempRequest = new RequestClasses.RegisterRequest("test", "test", "test");
        var tempResult = this.registerService.register(tempRequest);

        // Check that tempResult is correct
        Assertions.assertEquals(200, tempResult.getStatus());
        Assertions.assertEquals("test", tempResult.getUsername());
        Assertions.assertNotNull(tempResult.getAuthToken());

        // Try to logout
        var logoutRequest = new RequestClasses.LogoutRequest(tempResult.getAuthToken());
        var logoutResult = this.logoutService.logout(logoutRequest);

        // Check that logoutResult is correct
        Assertions.assertEquals(200, logoutResult.getStatus());

        // Check that the auth token was removed from the database
        Assertions.assertThrows(DataAccessException. class, () -> this.authDAO.checkAuthToken(tempResult.getAuthToken()));
    }

    @Test
    public void testLogoutServiceNegative() {
        // Try to logout non-existent user
        var logoutRequest = new RequestClasses.LogoutRequest(new AuthToken("test", "test"));
        var logoutResult = this.logoutService.logout(logoutRequest);

        // Check that logoutResult is correct
        Assertions.assertEquals(401, logoutResult.getStatus());
    }

    @Test
    public void testCreateGameServicePositive() {
        // Register a user
        var tempRequest = new RequestClasses.RegisterRequest("test", "test", "test");
        var tempResult = this.registerService.register(tempRequest);
        var authToken = tempResult.getAuthToken();

        // Create a game
        var createGameRequest = new RequestClasses.CreateGameRequest(authToken, "test");
        var createGameResult = this.createGameService.createGame(createGameRequest);

        // Check that createGameResult is correct
        Assertions.assertEquals(200, createGameResult.getStatus());
        Assertions.assertNotNull(createGameResult.getGameID());

        // Check that the game was added to the database
        try {
            var game = this.gameDAO.getGame(createGameResult.getGameID());
            Assertions.assertNotNull(game);
            Assertions.assertEquals("test", game.getGameName());
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Exception thrown");
        }
    }

    @Test
    public void testCreateGameServiceNegative() {
        // Attempt to create a game without logging in
        var createGameRequest = new RequestClasses.CreateGameRequest(null, "test");
        var createGameResult = this.createGameService.createGame(createGameRequest);

        // Check that createGameResult is correct
        Assertions.assertEquals(400, createGameResult.getStatus());
    }

    @Test
    public void testListGamesServicePositive() {
        // Register a User
        var tempRequest = new RequestClasses.RegisterRequest("test", "test", "test");
        var tempResult = this.registerService.register(tempRequest);
        var authToken = tempResult.getAuthToken();

        // Create some Games
        var createGameRequest = new RequestClasses.CreateGameRequest(authToken, "test");
        var createGameResult = this.createGameService.createGame(createGameRequest);
        var gameID1 = createGameResult.getGameID();

        createGameRequest = new RequestClasses.CreateGameRequest(authToken, "test");
        createGameResult = this.createGameService.createGame(createGameRequest);
        var gameID2 = createGameResult.getGameID();

        // List the Games
        var listGamesRequest = new RequestClasses.ListGamesRequest(authToken);
        var listGamesResult = this.listGamesService.listGames(listGamesRequest);

        // Check that listGamesResult is correct
        Assertions.assertEquals(200, listGamesResult.getStatus());
        Assertions.assertNotNull(listGamesResult.getGames());
        Assertions.assertEquals(2, listGamesResult.getGames().size());

        // Check that the games are in the result
        var tempGame = new Game();
        tempGame.setGameID(gameID1);
        Assertions.assertTrue(listGamesResult.getGames().contains(tempGame));
        tempGame.setGameID(gameID2);
        Assertions.assertTrue(listGamesResult.getGames().contains(tempGame));
    }

    @Test
    public void testListGamesServiceNegative() {
        // Attempt to list games without logging in
        var listGamesRequest = new RequestClasses.ListGamesRequest(null);
        var listGamesResult = this.listGamesService.listGames(listGamesRequest);

        // Check that listGamesResult is correct
        Assertions.assertEquals(400, listGamesResult.getStatus());
    }

    @Test
    public void testJoinGameServicePositive() {
        // Register a User
        var tempRequest = new RequestClasses.RegisterRequest("test", "test", "test");
        var tempResult = this.registerService.register(tempRequest);
        var authToken = tempResult.getAuthToken();

        // Create a Game
        var createGameRequest = new RequestClasses.CreateGameRequest(authToken, "test");
        var createGameResult = this.createGameService.createGame(createGameRequest);
        var gameID = createGameResult.getGameID();

        // Join the Game
        var joinGameRequest = new RequestClasses.JoinGameRequest(authToken, gameID, "WHITE");
        var joinGameResult = this.joinGameService.joinGame(joinGameRequest);

        // Check that joinGameResult is correct
        Assertions.assertEquals(200, joinGameResult.getStatus());

        // Check that the game was updated in the database
        try {
            var game = this.gameDAO.getGame(gameID);
            Assertions.assertNotNull(game);
            Assertions.assertEquals("test", game.getGameName());
            Assertions.assertEquals("test", game.getWhiteUsername());
            Assertions.assertNull(game.getBlackUsername());
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Exception thrown");
        }
    }

    @Test
    public void testJoinGameServiceNegative() {
        // Register a User
        var tempRequest = new RequestClasses.RegisterRequest("test", "test", "test");
        var tempResult = this.registerService.register(tempRequest);
        var authToken = tempResult.getAuthToken();

        // Create a Game
        var createGameRequest = new RequestClasses.CreateGameRequest(authToken, "test");
        var createGameResult = this.createGameService.createGame(createGameRequest);
        var gameID = createGameResult.getGameID();

        // Join the Game
        var joinGameRequest = new RequestClasses.JoinGameRequest(authToken, gameID, "WHITE");
        var joinGameResult = this.joinGameService.joinGame(joinGameRequest);

        // Check that joinGameResult is correct
        Assertions.assertEquals(200, joinGameResult.getStatus());

        // Join the same spot with new player
        var player2req = new RegisterRequest("test2", "test2", "test2");
        var player2res = this.registerService.register(player2req);
        var player2token = player2res.getAuthToken();
        var joinGameRequest2 = new RequestClasses.JoinGameRequest(player2token, gameID, "WHITE");
        var joinGameResult2 = this.joinGameService.joinGame(joinGameRequest2);

        // Check that joinGameResult2 is correct
        Assertions.assertEquals(403, joinGameResult2.getStatus());
    }
}
