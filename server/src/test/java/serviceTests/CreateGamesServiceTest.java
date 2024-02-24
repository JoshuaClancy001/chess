package serviceTests;

import server.Request.CreateGameRequest;
import server.Request.RegisterRequest;
import server.Result.CreateGameResult;
import dataAccess.DataAccessException;
import dataAccess.MemoryDAO.MemoryAuthDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearApplicationService;
import service.CreateGamesService;
import service.Services;

import java.util.UUID;

class CreateGamesServiceTest extends Services {
    String authToken = UUID.randomUUID().toString();
    int gameID = 1;

    @BeforeEach
    void setUp() throws DataAccessException {
        gameDAO.clearGames();
        userDao.clearUsers();
        authDAO  = new MemoryAuthDAO();
        RegisterRequest request = new RegisterRequest("username","password","email");
        authDAO.addAuth(authToken,request.username());
    }

    @Test
    void createGameSuccess() throws DataAccessException {
        gameDAO.clearGames();
        gameDAO.resetGameID();
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
    static void tearDown(){
        new ClearApplicationService().clearApplication();
    }
}