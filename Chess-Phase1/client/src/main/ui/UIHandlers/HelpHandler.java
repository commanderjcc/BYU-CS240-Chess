package ui.UIHandlers;

import ServerFacade.ServerFacade;
import ui.Printer;

/**
 * A class for handling help commands from the user.
 */
public class HelpHandler extends Handler {
    /**
     * Creates a new HelpHandler.
     *
     * @param serverFacade The server facade to use.
     */
    public HelpHandler(ServerFacade serverFacade) {
        super(serverFacade);
    }

    /**
     * Displays the help message.
     *
     * @param loggedIn Whether or not the user is logged in.
     */
    public void help(boolean loggedIn) {
        p.setColor(Printer.Color.YELLOW);
        p.println("Commands:");
        if (loggedIn) {
            help_postLogin();
        } else {
            help_preLogin();
        }
        p.setColor(Printer.Color.YELLOW);
        p.setIndent(4);
        p.print("quit");
        p.reset();
        p.println(" - Quit the program");

        p.setColor(Printer.Color.YELLOW);
        p.setIndent(4);
        p.print("help");
        p.reset();
        p.println(" - Display this help message");
    }

    /**
     * Displays the help message for logged in users.
     */
    public void help_postLogin() {
        p.setColor(Printer.Color.YELLOW);
        p.setIndent(4);
        p.print("create <name>");
        p.reset();
        p.println(" - Create a new game");

        p.setColor(Printer.Color.YELLOW);
        p.setIndent(4);
        p.print("list");
        p.reset();
        p.println(" - List all games");

        p.setColor(Printer.Color.YELLOW);
        p.setIndent(4);
        p.print("join <ID> [WHITE|BLACK|RANDOM]");
        p.reset();
        p.println(" - Join a game");

        p.setColor(Printer.Color.YELLOW);
        p.setIndent(4);
        p.print("observe <ID>");
        p.reset();
        p.println(" - Observe a game");

        p.setColor(Printer.Color.YELLOW);
        p.setIndent(4);
        p.print("logout");
        p.reset();
        p.println(" - Log out of your account");
    }

    /**
     * Displays the help message for users who are not logged in.
     */
    public void help_preLogin() {
        p.setColor(Printer.Color.YELLOW);
        p.setIndent(4);
        p.print("login <username> <password>");
        p.reset();
        p.println(" - Log in to your account");

        p.setColor(Printer.Color.YELLOW);
        p.setIndent(4);
        p.print("register <username> <password> <email>");
        p.reset();
        p.println(" - Create a new account");
    }
}
