package webSocketMessages.userCommands;

import chess.ChessMove;

public class MakeMove extends UserGameCommand{

    int gameID;
    ChessMove move;
    public MakeMove(String authToken,int gameID,ChessMove move,CommandType type) {
        super(authToken);
        this.gameID = gameID;
        this.move = move;
        this.commandType = type;
    }

    public int getGameID() {
        return gameID;
    }

    public ChessMove getMove() {
        return move;
    }
}
