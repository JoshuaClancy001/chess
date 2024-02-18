package Request;

public record JoinGameRequest(String authToken,String clientColor,int gameID) {
}
