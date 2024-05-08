package ui.GameplayHandlers;

import Exceptions.ResponseException;
import Models.AuthToken;
import WSFacade.WSFacade;
import chess.ChessGame;

/**
 * The GJoinHandler class is responsible for handling the join command.
 * It extends the OnlineGameHandler class and provides methods to join a game.
 */
public class GJoinHandler extends OnlineGameHandler {
    /**
     * Constructs a GJoinHandler object with the given WSFacade and AuthToken.
     *
     * @param wsFacade  The WSFacade to use for sending messages to the server.
     * @param authToken The AuthToken to use for authentication.
     */
    public GJoinHandler(WSFacade wsFacade, AuthToken authToken) {
        super(wsFacade, authToken);
    }


    /**
     * Joins a game with the specified game ID and team color.
     *
     * @param gameId     The ID of the game to join.
     * @param teamColor  The team color to join as.
     * @throws ResponseException If an error occurs while sending the join request.
     */
    public void joinGame(Integer gameId, ChessGame.TeamColor teamColor) throws ResponseException {
        wsFacade.sendJoinPlayer(authToken, gameId, teamColor);
    }
}
