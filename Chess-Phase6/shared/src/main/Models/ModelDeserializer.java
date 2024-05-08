package Models;

import ResultClasses.LoginResult;
import ResultClasses.RegisterResult;
import chess.ChessGame;
import chessGameImpl.CGame;
import com.google.gson.*;

import java.io.InputStreamReader;

/**
 * A class for deserializing results from the ServerFacade
 */
public class ModelDeserializer {
    public static <T> T deserialize(InputStreamReader reader, Class<T> responseClass) {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(ChessGame.class, new CGame.ChessGameTA());
        gsonBuilder.registerTypeAdapter(LoginResult.class, new LoginResult.LoginResultTypeAdapter());
        gsonBuilder.registerTypeAdapter(RegisterResult.class, new RegisterResult.RegisterResultTypeAdapter());
        return gsonBuilder.create().fromJson(reader, responseClass);
    }
}
