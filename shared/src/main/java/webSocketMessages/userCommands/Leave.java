package webSocketMessages.userCommands;

public class Leave extends UserGameCommand{
    int gameID;
    public Leave(String authToken,int gameID,CommandType type) {
        super(authToken);
        this.gameID = gameID;
        this.commandType = type;
    }

    public int getGameID() {
        return gameID;
    }
}
