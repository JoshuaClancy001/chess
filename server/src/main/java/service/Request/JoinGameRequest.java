package service.Request;

public record JoinGameRequest(String authToken,String playerColor,int gameID) {
}
