package WebSockets.CommandHandlers;

import Services.GameService;
import WebSockets.WSSessionsManager;
import chess.ChessGame;
import chess.InvalidMoveException;
import dataAccess.DataAccessException;
import webSocketMessages.userCommands.MoveCommand;
import webSocketMessages.userCommands.UserGameCommand;

import org.eclipse.jetty.websocket.api.Session;
import java.io.IOException;

/**
 * This class represents a command handler for the "move" command in the WebSocket API.
 * It handles the logic for when a player wants to make a move in a game.
 */
public class MoveCommandHandler extends CommandHandler {
    /**
     * Constructs a MoveCommandHandler with the specified WSSessionsManager and GameService.
     * 
     * @param sessionsManager The WSSessionsManager used to manage WebSocket sessions.
     * @param gameService     The GameService used to interact with the game logic.
     */
    public MoveCommandHandler(WSSessionsManager sessionsManager, GameService gameService) {
        super(sessionsManager, gameService);
    }

    /**
     * Handles the user's game move command.
     * 
     * @param session the WebSocket session of the user
     * @param command the user's game move command
     * @throws IOException if there is an error in the input/output
     * @throws InvalidMoveException if the move is invalid
     * @throws DataAccessException if there is an error accessing the data
     */
    public void handle(Session session, UserGameCommand command) throws IOException, InvalidMoveException, DataAccessException {
        MoveCommand moveCommand = (MoveCommand) command;
        var gameID = moveCommand.getGameID();
        var username = moveCommand.getUsername();
        var move = moveCommand.getMove();
        var game = gameService.getGame(gameID);
        ChessGame.TeamColor playerTeam;

        //Check if player is in game
        if (game.getWhiteUsername().equals(username)) {
            playerTeam = ChessGame.TeamColor.WHITE;
        } else if (game.getBlackUsername().equals(username)) {
            playerTeam = ChessGame.TeamColor.BLACK;
        } else {
            session.getRemote().sendString(gson.toJson(new webSocketMessages.serverMessages.ErrorMessage("Error: Player not in game")));
            return;
        }

        //Check turn
        var chessGame = game.getGame();
        if (chessGame.getTeamTurn() != playerTeam) {
            session.getRemote().sendString(gson.toJson(new webSocketMessages.serverMessages.ErrorMessage("Error: Not your turn")));
            return;
        }

        //Check if move is valid
        var moves = chessGame.validMoves(move.getStartPosition());
        if (moves == null) {
            session.getRemote().sendString(gson.toJson(new webSocketMessages.serverMessages.ErrorMessage("Error: Invalid move")));
            return;
        }

        if (!chessGame.validMoves(move.getStartPosition()).contains(move)) {
            session.getRemote().sendString(gson.toJson(new webSocketMessages.serverMessages.ErrorMessage("Error: Invalid move")));
            return;
        }

        //Check if game is over
        if (game.isOver()) {
            session.getRemote().sendString(gson.toJson(new webSocketMessages.serverMessages.ErrorMessage("Error: Game is over")));
            return;
        }

        game.getGame().makeMove(move);
        gameService.updateGame(game);
        sessionsManager.broadcast(gameID, new webSocketMessages.serverMessages.LoadGameMessage(game.getGame()), "");

        //flip player team
        playerTeam = playerTeam == ChessGame.TeamColor.BLACK ? ChessGame.TeamColor.WHITE : ChessGame.TeamColor.BLACK;

        //Broadcast move
        var message = String.format("Player %s moved %s", username, move);
        var notification = new webSocketMessages.serverMessages.NotificationMessage(message);
        sessionsManager.broadcast(gameID, notification, username);

        //Switch username
        username = playerTeam == ChessGame.TeamColor.WHITE ? game.getWhiteUsername() : game.getBlackUsername();

        //Check if player is in Check
        if (game.getGame().isInCheck(playerTeam)) {
            var alert = String.format("Player %s is in check", username);
            var notif = new webSocketMessages.serverMessages.NotificationMessage(alert);
            sessionsManager.broadcast(gameID, notif, "");
        }

        //Check if player is in Checkmate
        if (game.getGame().isInCheckmate(playerTeam)) {
            var alert = String.format("Player %s is in checkmate", username);
            var notif = new webSocketMessages.serverMessages.NotificationMessage(alert);
            sessionsManager.broadcast(gameID, notif, "");
        }


    }
}
