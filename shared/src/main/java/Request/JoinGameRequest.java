package Request;

public record JoinGameRequest(String authToken,String playerColor,int gameID) {
}
