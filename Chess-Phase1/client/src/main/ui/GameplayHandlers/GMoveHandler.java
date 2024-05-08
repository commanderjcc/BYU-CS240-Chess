package ui.GameplayHandlers;

import Exceptions.ResponseException;
import Models.AuthToken;
import WSFacade.WSFacade;
import chess.ChessMove;
import chess.ChessPiece;
import chessGameImpl.CMove;
import chessGameImpl.CPosition;
import ui.Printer;

/**
 * The GMoveHandler class is responsible for handling the move command.
 * It extends the OnlineGameHandler class and provides methods to extract the move from passed args,
 * as well as send the move to the server.
 */
public class GMoveHandler extends OnlineGameHandler {
    /**
     * Constructs a GMoveHandler object with the given WSFacade and AuthToken.
     * @param wsFacade The wsFacade to use for sending messages
     * @param authToken The authToken to send with those messages
     */
    public GMoveHandler(WSFacade wsFacade, AuthToken authToken) {
        super(wsFacade, authToken);
    }

    /**
     * Extracts the move from the passed args and sends the move to the server.
     * @param gameID The ID of the game to send the move to.
     * @param args The arguments passed to the command.
     * @throws ResponseException If an error occurs while sending the move request.
     */
    public void extractMoveAndSend(Integer gameID, String[] args) throws ResponseException {
        if (args.length < 2) {
            p.reset();
            p.setColor(Printer.Color.RED);
            p.println("Invalid number of arguments");
            new GHelpHandler().help();
            return;
        }

        // Convert from letter-number to x-y
        var position = args[1];
        var y1 = position.charAt(0) - 'a' + 1;
        var x1 = position.charAt(1) - '1' + 1;
        var y2 = position.charAt(2) - 'a' + 1;
        var x2 = position.charAt(3) - '1' + 1;

        var cPosition = new CPosition(x1, y1);
        var cPosition2 = new CPosition(x2, y2);

        ChessPiece.PieceType pieceType = null;
        if (args.length == 3) {
            //Check if promotion
            switch (args[2]) {
                case "q" -> pieceType = ChessPiece.PieceType.QUEEN;
                case "r" -> pieceType = ChessPiece.PieceType.ROOK;
                case "b" -> pieceType = ChessPiece.PieceType.BISHOP;
                case "n" -> pieceType = ChessPiece.PieceType.KNIGHT;
                default -> {
                    p.reset();
                    p.setColor(Printer.Color.RED);
                    p.println("Invalid promotion");
                    new GHelpHandler().help();
                    return;
                }
            }
        }

        var move = new CMove(cPosition, cPosition2, pieceType);
        move(gameID, move);
    }

    /**
     * Sends the given move to the server.
     * @param gameID The ID of the game to send the move to.
     * @param move The move to send to the server.
     * @throws ResponseException If an error occurs while sending the move request.
     */
    public void move(Integer gameID, ChessMove move) throws ResponseException {
        wsFacade.sendMove(authToken, gameID, move);
    }
}
