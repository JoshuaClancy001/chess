package service;

import dataAccess.DataAccessException;
import model.AuthData;
import model.GameData;
import Request.CreateGameRequest;
import Result.CreateGameResult;

public class CreateGamesService extends Services {

    CreateGameRequest createGameRequest;

    public CreateGamesService(CreateGameRequest createGame)throws DataAccessException{
        this.createGameRequest = createGame;
    }

    public CreateGameResult createGame(String authToken,CreateGameRequest createGameRequest)throws DataAccessException {

        AuthData data = getAuth(authToken);

        if (data == null){
            throw new DataAccessException(401,"Unauthorized");
        }

        GameData game = addGame(createGameRequest.gameName());

        return new CreateGameResult(game.getGameID());
    }
}
