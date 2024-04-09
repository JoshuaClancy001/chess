package webSocketMessages.userCommands;

public class JoinObserver extends UserGameCommand{

    private int gameID;
    public JoinObserver(String authToken,CommandType commandType,int gameID) {
        super(authToken);
        this.commandType = commandType;
        this.gameID = gameID;

    }

    public int getGameID() {
        return gameID;
    }
}
