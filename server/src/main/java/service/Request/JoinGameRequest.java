package service.Request;

public record JoinGameRequest(String authToken,String clientColor,int gameID) {
}
