package webSocketMessages.userCommands;

public class Resign extends UserGameCommand{
    int gameID;
    public Resign(String authToken, int gameID,CommandType commandType) {
        super(authToken);
        this.gameID = gameID;
        this.commandType = commandType;
    }

    public int getGameID() {
        return gameID;
    }
}
