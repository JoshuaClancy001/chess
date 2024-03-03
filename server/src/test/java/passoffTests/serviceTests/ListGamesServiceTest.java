package passoffTests.serviceTests;

import dataAccess.SQLDAO.SQLAUTHDAO;
import server.Request.ListGamesRequest;
import server.Request.RegisterRequest;
import server.Result.ListGamesResult;
import dataAccess.DataAccessException;
import dataAccess.MemoryDAO.MemoryAuthDAO;
import model.GameData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearApplicationService;
import service.ListGamesService;
import service.Services;

import java.util.ArrayList;
import java.util.UUID;

class ListGamesServiceTest extends Services {

    String authToken = UUID.randomUUID().toString();
    @BeforeEach
    void setUp() throws DataAccessException {
        authDao  = new MemoryAuthDAO();
        RegisterRequest request = new RegisterRequest("username","password","email");

    }

    @Test
    void listGamesSuccess() throws DataAccessException {
        ListGamesRequest request = new ListGamesRequest(authToken);
        gameDao.addGame("game1");
        ArrayList<GameData> expected = gameDao.readGame();
        ListGamesResult result = new ListGamesService(request).listGames(authToken,request);

        Assertions.assertEquals(expected,result.games());

    }

    @Test
    void listGamesFailure(){
        ListGamesRequest request = new ListGamesRequest("wrong");

        Assertions.assertThrows(DataAccessException.class,()-> new ListGamesService(request).listGames("wrong",request));


    }

    @AfterAll
    static void tearDown() throws DataAccessException {
        new ClearApplicationService().clearApplication();
    }
}