package webSocketMessages.serverMessages;

import chess.ChessGame;
import chessGameImpl.CGame;
import com.google.gson.GsonBuilder;

import java.util.Objects;

/**
 * Represents a server message for loading a chess game.
 */
public class LoadGameMessage extends ServerMessage {

    ChessGame game;

    /**
     * Constructs a LoadGameMessage object with the specified ChessGame.
     * 
     * @param game The ChessGame to be loaded.
     */
    public LoadGameMessage(ChessGame game) {
        super(ServerMessageType.LOAD_GAME);
        this.game = game;
    }

    /**
     * Constructs a LoadGameMessage object by parsing the game string using a type adapter.
     * 
     * @param game The game string to be parsed and loaded as a ChessGame.
     */
    public LoadGameMessage(String game) {
        super(ServerMessageType.LOAD_GAME);
        var GSON = new GsonBuilder().registerTypeAdapter(ChessGame.class, new CGame.ChessGameTA()).create();
        this.game = GSON.fromJson(game, ChessGame.class);
    }

    /**
     * Gets the ChessGame associated with this LoadGameMessage.
     * 
     * @return The ChessGame object.
     */
    public ChessGame getGame() {
        return game;
    }

    /**
     * Sets the ChessGame associated with this LoadGameMessage.
     * 
     * @param game The ChessGame to be set.
     */
    public void setGame(ChessGame game) {
        this.game = game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LoadGameMessage that = (LoadGameMessage) o;
        return Objects.equals(game, that.game);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), game);
    }
}
