package service;

import dataAccess.DataAccessException;
import model.AuthData;
import Request.ListGamesRequest;
import Result.ListGamesResult;

public class ListGamesService extends Services {

    private ListGamesRequest listGames;

    public ListGamesService(ListGamesRequest listGames) {
        this.listGames = listGames;
    }
    public ListGamesResult listGames(String authToken, ListGamesRequest listGamesRequest)throws DataAccessException{

        AuthData data = getAuth(authToken);

        if (data == null){
            throw new DataAccessException("Unauthorized");
        }

        return new ListGamesResult(listGames());

    }
}
