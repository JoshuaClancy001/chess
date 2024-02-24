package service;

import dataAccess.DataAccessException;
import model.AuthData;
import service.Request.CreateGameRequest;
import service.Request.JoinGameRequest;
import service.Result.JoinGameResult;

public class JoinGamesService extends Services {

    private JoinGameRequest joinGameRequest;

    public JoinGamesService(JoinGameRequest joinGameRequest){
        this.joinGameRequest = joinGameRequest;
    }
    public JoinGameResult joinGame(String authToken, JoinGameRequest joinGameRequest)throws DataAccessException {

        AuthData data = getAuth(authToken);

        if (data == null){
            throw new DataAccessException("Unauthorized");
        }

        addPlayer(joinGameRequest.clientColor(),data.username());

        return new JoinGameResult(joinGameRequest.clientColor(), joinGameRequest.gameID());
    }
}
