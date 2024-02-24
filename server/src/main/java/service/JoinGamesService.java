package service;

import dataAccess.DataAccessException;
import model.AuthData;
import server.Request.JoinGameRequest;
import server.Result.JoinGameResult;

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

        addPlayer(joinGameRequest.playerColor(),data.username(),joinGameRequest.gameID());

        return new JoinGameResult(joinGameRequest.playerColor(), joinGameRequest.gameID());
    }
}
