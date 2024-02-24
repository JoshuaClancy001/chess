package service;

import dataAccess.DataAccessException;
import model.AuthData;
import model.GameData;
import service.Request.CreateGameRequest;
import service.Result.CreateGameResult;

public class CreateGamesService extends Services {

    CreateGameRequest createGameRequest;

    public CreateGamesService(CreateGameRequest createGame){
        this.createGameRequest = createGame;
    }

    public CreateGameResult createGame(String authToken,CreateGameRequest createGameRequest)throws DataAccessException {

        AuthData data = getAuth(authToken);

        if (data == null){
            throw new DataAccessException("Unauthorized");
        }

        GameData game = addGame(createGameRequest.gameName());

        return new CreateGameResult(game.getGameID());
    }
}
