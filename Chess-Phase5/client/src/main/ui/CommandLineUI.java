package ui;

import java.util.List;
import java.util.Scanner;

import Exceptions.ResponseException;
import Models.AuthToken;
import Models.Game;
import ServerFacade.ServerFacade;
import ui.UIHandlers.*;

/**
 * The command line user interface
 */
public class CommandLineUI {

    /**
     * The state of the command line interface
     */
    private enum State {
        PRE_LOGIN,
        LOGGED_IN,
        GAMEPLAY
    }

    // Pretty Printer
    private final Printer p = new Printer();

    // Server Facade
    private final ServerFacade serverFacade = new ServerFacade("http://localhost:8080");

    // GameplayUI
    private GameplayUI gameplayUI;

    // Handlers
    private final HelpHandler helpHandler = new HelpHandler(serverFacade);
    private final LoginHandler loginHandler = new LoginHandler(serverFacade);
    private final RegisterHandler registerHandler = new RegisterHandler(serverFacade);
    private final LogoutHandler logoutHandler = new LogoutHandler(serverFacade);
    private final CreateHandler createHandler = new CreateHandler(serverFacade);
    private final JoinHandler joinHandler = new JoinHandler(serverFacade);
    private final ListHandler listHandler = new ListHandler(serverFacade);
    private final ObserveHandler observeHandler = new ObserveHandler(serverFacade);

    // State variables
    private AuthToken authToken;
    private String username;
    private List<Game> gameList;
    private State state;

    /**
     * The main REPL loop
     * @param input The input from the user
     * @return Whether or not the REPL should continue
     */
    public boolean replLoop(String input) {
        var args = input.split(" ");
        if (args.length == 0) {
            return true;
        }


        switch (args[0]) {
            case "help" -> helpHandler.help(this.state == State.LOGGED_IN);
            case "quit" -> {
                return false;
            }
            case "login" -> checkStateAndLogin(args);
            case "register" -> checkStateAndRegister(args);
            case "logout" -> checkStateAndLogout();
            case "list" -> checkStateAndList();
            case "join" -> checkStateAndJoin(args);
            case "create" -> checkStateAndCreateGame(args);
            case "observe" -> checkStateAndObserve(args);
            default -> {
                p.reset();
                p.setColor(Printer.Color.RED);
                p.println("Unknown command: " + input);
                helpHandler.help(this.state == State.LOGGED_IN);
            }
        }

        return true;
    }

    /**
     * Checks the state and attempts to observe a game
     * @param args a string array of arguments including the game index to observe in the 1st position
     */
    private void checkStateAndObserve(String[] args) {
        if (this.state == State.LOGGED_IN) {
            if (this.gameList == null) {
                p.reset();
                p.setColor(Printer.Color.RED);
                p.println("You must list games first!");
            } else {
                var result = observeHandler.observe(args, this.authToken, this.gameList);
                if (result != null) {
                    this.state = State.GAMEPLAY;
                    try {
                        this.gameplayUI = new GameplayUI(this.authToken, "http://localhost:8080", null, result.getGameID());
                        gameplayUI.observe();
                    } catch (ResponseException e) {
                        p.println("Error observing game");
                    }
                }
                boolean inGame = true;
                while (inGame) {
                    Scanner scanner = new Scanner(System.in);
                    p.reset();
                    p.setColor(Printer.Color.GREEN);
                    p.print("[" + username + "] >>> ");
                    p.setColor(Printer.Color.YELLOW);
                    p.print("");
                    String input = scanner.nextLine();
                    inGame = gameplayUI.replLoop(input);
                }
                this.state = State.LOGGED_IN;
            }
        } else {
            p.reset();
            p.setColor(Printer.Color.RED);
            p.println("You are not logged in!");
        }
    }

    /**
     * Checks the state and attempts to create a game
     * @param args a string array of arguments including the game name in the 1st position
     */
    private void checkStateAndCreateGame(String[] args) {
        if (this.state == State.LOGGED_IN) {
            var result = createHandler.create(args, this.authToken);
            if (result == null) {
                p.reset();
                p.setColor(Printer.Color.RED);
                p.println("Error creating game");
            }
        } else {
            p.reset();
            p.setColor(Printer.Color.RED);
            p.println("You are not logged in!");
        }
    }

    /**
     * Checks the state and attempts to join a game
     * @param args a string array of arguments including the game index to join in the 1st position and the player
     *             color in the 2nd position
     */
    private void checkStateAndJoin(String[] args) {
        if (this.state == State.LOGGED_IN) {
            if (this.gameList == null) {
                p.reset();
                p.setColor(Printer.Color.RED);
                p.println("You must list games first!");
            } else {
                var result = joinHandler.join(args, this.authToken, this.gameList);
                if (result != null) {
                    this.state = State.GAMEPLAY;
                    try {
                        this.gameplayUI = new GameplayUI(this.authToken, "http://localhost:8080", result.getPlayerColor(), result.getGameID());
                        gameplayUI.join();
                    } catch (ResponseException e) {
                        p.println("Error joining game");
                    }
                    boolean inGame = true;
                    while (inGame) {
                        Scanner scanner = new Scanner(System.in);
                        p.reset();
                        p.setColor(Printer.Color.GREEN);
                        p.print("[" + username + "] >>> ");
                        p.setColor(Printer.Color.YELLOW);
                        p.print("");
                        String input = scanner.nextLine();
                        inGame = gameplayUI.replLoop(input);
                    }
                    this.state = State.LOGGED_IN;
                }
            }
        } else {
            p.reset();
            p.setColor(Printer.Color.RED);
            p.println("You are not logged in!");
        }
    }

    /**
     * Checks the state and attempts to list games
     */
    private void checkStateAndList() {
        if (this.state == State.LOGGED_IN) {
            this.gameList = listHandler.list(this.authToken);
        } else {
            p.reset();
            p.setColor(Printer.Color.RED);
            p.println("You are not logged in!");
        }
    }

    /**
     * Checks the state and attempts to logout
     */
    private void checkStateAndLogout() {
        if (this.state == State.LOGGED_IN) {
            logoutHandler.logout(this.authToken);
            this.state = State.PRE_LOGIN;
            p.println("Logged out!");
        } else {
            p.reset();
            p.setColor(Printer.Color.RED);
            p.println("You are not logged in!");
        }
    }

    /**
     * Checks the state and attempts to register
     * @param args a string array of arguments including the username in the 1st position and the password in the 2nd
     *             position, and the email in the 3rd position
     */
    private void checkStateAndRegister(String[] args) {
        if (this.state == State.PRE_LOGIN) {
            this.authToken = registerHandler.register(args);
            if (this.authToken != null) {
                this.state = State.LOGGED_IN;
                this.username = args[1];
            }
        } else {
            p.reset();
            p.setColor(Printer.Color.RED);
            p.println("You are already logged in!");
        }
    }

    /**
     * Checks the state and attempts to login
     * @param args a string array of arguments including the username in the 1st position and the password in the 2nd
     *             position
     */
    private void checkStateAndLogin(String[] args) {
        if (this.state == State.PRE_LOGIN) {
            this.authToken = loginHandler.login(args);
            if (this.authToken != null) {
                this.state = State.LOGGED_IN;
                this.username = args[1];
            }
        } else {
            p.reset();
            p.setColor(Printer.Color.RED);
            p.println("You are already logged in!");
        }
    }

    /**
     * Constructor
     */
    public CommandLineUI() {
        this.state = State.PRE_LOGIN;
    }

    /**
     * The main run loop
     */
    public void run() {
        initMessage();
        Scanner scanner = new Scanner(System.in);
        boolean shouldContinue = true;
        while (shouldContinue) {
            p.reset();
            p.setColor(Printer.Color.GREEN);
            if (this.state == State.PRE_LOGIN) {
                p.print("[240Chess] >>> ");
            } else {
                p.print("[" + username + "] >>> ");
            }
            p.setColor(Printer.Color.YELLOW);
            p.print("");
            String input = scanner.nextLine();
            shouldContinue = this.replLoop(input);
        }
    }

    /**
     * The main method, creates instance of CommandLineUI and runs it
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        var self = new CommandLineUI();
        self.run();
    }

    /**
     * Prints the welcome message
     */
    private void initMessage() {
        p.setThickness(Printer.Thickness.BOLD);
        p.setColor(Printer.Color.YELLOW);
        p.println("Welcome to 240 Chess!");
        p.println("Type \"help\" for help");
    }
}
