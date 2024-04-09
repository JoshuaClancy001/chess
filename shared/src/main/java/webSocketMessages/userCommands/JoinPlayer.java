package webSocketMessages.userCommands;

public class JoinPlayer extends UserGameCommand{

    private int gameID;
    private String playerColor;
    public JoinPlayer(String authToken, CommandType commandType,int gameID, String playerColor) {
        super(authToken);
        this.commandType = commandType;
        this.gameID = gameID;
        this.playerColor = playerColor;
    }

    public String getPlayerColor() {
        return playerColor;
    }

    public int getGameID() {
        return gameID;
    }

}
