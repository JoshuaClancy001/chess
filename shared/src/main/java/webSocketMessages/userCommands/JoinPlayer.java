package webSocketMessages.userCommands;

public class JoinPlayer extends UserGameCommand{

    private int gameID;
    private String username;
    private String clientColor;
    public JoinPlayer(String authToken, CommandType commandType,int gameID, String username, String clientColor ) {
        super(authToken);
        this.commandType = commandType;
        this.username = username;
        this.clientColor = clientColor;
        this.gameID = gameID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClientColor() {
        return clientColor;
    }

    public void setClientColor(String clientColor) {
        this.clientColor = clientColor;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}
