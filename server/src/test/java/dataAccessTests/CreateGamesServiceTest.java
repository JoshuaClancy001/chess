package dataAccessTests;

import dataAccess.SQLDAO.SQLAUTHDAO;
import Request.CreateGameRequest;
import Request.RegisterRequest;
import Result.CreateGameResult;
import dataAccess.DataAccessException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Result.RegisterResult;
import service.ClearApplicationService;
import service.CreateGamesService;
import service.RegistrationService;
import service.Services;

class CreateGamesServiceTest extends Services {
    String authToken;
    int gameID = 1;

    @BeforeEach
    void setUp() throws DataAccessException {
        gameDao.clearGames();
        userDao.clearUsers();
        authDao  = new SQLAUTHDAO();
        RegisterRequest request = new RegisterRequest("username","password","email");
        RegisterResult result = new RegistrationService(request).register(request);
        authToken = result.authToken();
    }

    @Test
    void createGameSuccess() throws DataAccessException {
        gameDao.clearGames();
        CreateGameRequest request = new CreateGameRequest(authToken,"game1");
        CreateGameResult result = new CreateGamesService(request).createGame(authToken,request);
        int actual = result.gameID();
        int expected = gameID;

        Assertions.assertEquals(expected,actual);


    }

    @Test
    void createGameFailure(){
        CreateGameRequest request = new CreateGameRequest("wrong","game1");

        Assertions.assertThrows(DataAccessException.class,()-> new CreateGamesService(request).createGame("wrong",request));

    }
    @AfterAll
    static void tearDown() throws DataAccessException {
        new ClearApplicationService().clearApplication();
    }
}