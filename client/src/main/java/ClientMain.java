import chess.InvalidMoveException;
import ui.Exception.ResponseException;

import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) throws ResponseException, InvalidMoveException, IOException {
        var serverUrl = "http://localhost:8080";
        if (args.length == 1) {
            serverUrl = args[0];
        }

        new Main(serverUrl).Run();
    }

}