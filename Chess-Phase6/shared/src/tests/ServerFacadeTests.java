import Exceptions.ResponseException;
import ResultClasses.CreateGameResult;
import ResultClasses.LoginResult;
import ResultClasses.RegisterResult;
import org.junit.jupiter.api.*;
import ServerFacade.ServerFacade;

public class ServerFacadeTests {

    private final ServerFacade serverFacade = new ServerFacade("http://localhost:8080");

    @BeforeAll
    public static void setUp() {
        try {
            new ServerFacade("http://localhost:8080").clear();
        } catch (ResponseException e) {
            Assertions.fail();
        }
    }

    @BeforeEach
    public void setUp2() {
        try {
            var s = new ServerFacade("http://localhost:8080");
            s.clear();
            s.register("hi", "hi", "hi");
        } catch (ResponseException e) {
            Assertions.fail();
        }
    }

    @Test
    public void testRegisterPositive() {
        try {
            RegisterResult registerResult = serverFacade.register("username", "password", "email");
            Assertions.assertNotNull(registerResult);
            Assertions.assertNotNull(registerResult.getAuthToken());
        } catch (ResponseException e) {
            Assertions.fail();
        }
    }

    @Test
    public void testRegisterNegative() {
        try {
            RegisterResult registerResult = serverFacade.register("hi", "hi", "hi");
            Assertions.fail();
        } catch (ResponseException e) {
            Assertions.assertEquals(500, e.StatusCode());
        }
    }

    @Test
    public void testLoginPositive() {
        try {
            LoginResult loginResult = serverFacade.login("hi", "hi");
            Assertions.assertNotNull(loginResult);
            Assertions.assertNotNull(loginResult.getAuthToken());
        } catch (ResponseException e) {
            Assertions.fail();
        }
    }

    @Test
    public void testLoginNegative() {
        try {
            LoginResult loginResult = serverFacade.login("username", "wrongpassword");
            Assertions.fail();
        } catch (ResponseException e) {
            Assertions.assertEquals(500, e.StatusCode());
        }
    }

    @Test
    public void testLogoutPositive() {
        try {
            LoginResult loginResult = serverFacade.login("hi", "hi");
            Assertions.assertNotNull(loginResult);
            Assertions.assertNotNull(loginResult.getAuthToken());
            serverFacade.logout(loginResult.getAuthToken());
        } catch (ResponseException e) {
            Assertions.fail();
        }
    }

    @Test
    public void testLogoutNegative() {
        try {
            LoginResult loginResult = serverFacade.login("hi", "hi");
            Assertions.assertNotNull(loginResult);
            Assertions.assertNotNull(loginResult.getAuthToken());
            serverFacade.logout(loginResult.getAuthToken());
            serverFacade.logout(loginResult.getAuthToken());
            Assertions.fail(); //use assert throws
        } catch (ResponseException e) {
            Assertions.assertEquals(500, e.StatusCode());
        }
    }

    @Test
    public void testClear() {
        try {
            serverFacade.clear();
        } catch (ResponseException e) {
            Assertions.fail();
        }
    }

    @Test
    public void testListGamesPositive() {
        try {
            LoginResult loginResult = serverFacade.login("hi", "hi");
            Assertions.assertNotNull(loginResult);
            Assertions.assertNotNull(loginResult.getAuthToken());
            var res = serverFacade.listGames(loginResult.getAuthToken());
            Assertions.assertNotNull(res);
        } catch (ResponseException e) {
            Assertions.fail();
        }
    }

    @Test
    public void testListGamesNegative() {
        try {
            serverFacade.listGames(null);
            Assertions.fail();
        } catch (ResponseException e) {
            Assertions.assertEquals(500, e.StatusCode());
        }
    }

    @Test
    public void testJoinGamePositive() {
        try {
            LoginResult loginResult = serverFacade.login("hi", "hi");
            Assertions.assertNotNull(loginResult);
            Assertions.assertNotNull(loginResult.getAuthToken());
            CreateGameResult createGameResult = serverFacade.createGame(loginResult.getAuthToken(), "test");
            Assertions.assertNotNull(createGameResult);
            Assertions.assertNotNull(createGameResult.getGameID());
            var res = serverFacade.joinGame(loginResult.getAuthToken(), createGameResult.getGameID(), null);
            Assertions.assertNotNull(res);
        } catch (ResponseException e) {
            Assertions.fail();
        }
    }

    @Test
    public void testJoinGameNegative() {
        try {
            LoginResult loginResult = serverFacade.login("hi", "hi");
            Assertions.assertNotNull(loginResult);
            Assertions.assertNotNull(loginResult.getAuthToken());
            serverFacade.joinGame(loginResult.getAuthToken(), 0, "WHITE");
            serverFacade.joinGame(loginResult.getAuthToken(), 0, "WHITE");
            Assertions.fail();
        } catch (ResponseException e) {
            Assertions.assertEquals(500, e.StatusCode());
        }
    }

    @Test
    public void testCreateGamePositive() {
        try {
            LoginResult loginResult = serverFacade.login("hi", "hi");
            Assertions.assertNotNull(loginResult);
            Assertions.assertNotNull(loginResult.getAuthToken());
            serverFacade.createGame(loginResult.getAuthToken(), "test");
        } catch (ResponseException e) {
            Assertions.fail();
        }
    }

    @Test
    public void testCreateGameNegative() {
        try {
            serverFacade.createGame(null, "test");
            Assertions.fail();
        } catch (ResponseException e) {
            Assertions.assertEquals(500, e.StatusCode());
        }
    }
}
