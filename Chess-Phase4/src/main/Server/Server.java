package Server;

import DAOs.*;
import Handlers.*;
import Services.*;
import spark.Spark;

public class Server {

    // DAOs
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;
    private final UserDAO userDAO;

    // Services
    private final LoginService loginService;
    private final ClearDBService clearDBService;
    private final RegisterService registerService;
    private final CreateGameService createGameService;
    private final JoinGameService joinGameService;
    private final ListGamesService listGamesService;
    private final LogoutService logoutService;

    // Handlers
    private final LoginHandler loginHandler;
    private final LogoutHandler logoutHandler;
    private final ClearDBHandler clearDBHandler;
    private final RegisterHandler registerHandler;
    private final CreateGameHandler createGameHandler;
    private final JoinGameHandler joinGameHandler;
    private final ListGamesHandler listGamesHandler;


    /**
     * This is the main method which makes use of run method.
     * @param args Unused.
     */
    public static void main(String[] args) {
        new Server().run();
    }

    public Server() {
        //Init databases
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
    }

    /**
     * This method is used to start the server.
     */
    private void run() {
        // Specify the server's port number
        Spark.port(8080);

        // Specify the location of the static files
        Spark.externalStaticFileLocation("web");

        // Register Handlers for each endpoint
        Spark.delete("/db", this.clearDBHandler);
        Spark.post("/user", this.registerHandler);
        Spark.post("/session", this.loginHandler);
        Spark.delete("/session", this.logoutHandler);
        Spark.post("/game", this.createGameHandler);
        Spark.get("/game", this.listGamesHandler);
        Spark.put("/game", this.joinGameHandler);

        // Start the server
        Spark.init();
    }
}