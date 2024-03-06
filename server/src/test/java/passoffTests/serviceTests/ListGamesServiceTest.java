package passoffTests.serviceTests;

import com.google.gson.Gson;
import dataAccess.SQLDAO.SQLAUTHDAO;
import server.Request.CreateGameRequest;
import server.Request.ListGamesRequest;
import server.Request.LoginRequest;
import server.Request.RegisterRequest;
import server.Result.CreateGameResult;
import server.Result.ListGamesResult;
import dataAccess.DataAccessException;
import dataAccess.MemoryDAO.MemoryAuthDAO;
import model.GameData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Result.LoginResult;
import server.Result.RegisterResult;
import service.*;

import java.util.ArrayList;
import java.util.UUID;

class ListGamesServiceTest extends Services {

    String authToken = UUID.randomUUID().toString();
    @BeforeEach
    void setUp() throws DataAccessException {
        new ClearApplicationService().clearApplication();
        authDao  = new SQLAUTHDAO();
        RegisterRequest request = new RegisterRequest("username","password","email");
        RegisterResult registerResult = new RegistrationService(request).register(request);
        LoginRequest loginRequest = new LoginRequest("username","password");
        LoginResult loginResult = new LoginService(loginRequest).login(loginRequest);
        authToken = loginResult.authToken();

    }

    @Test
    void listGamesSuccess() throws DataAccessException {
        CreateGameRequest createGameRequest = new CreateGameRequest(authToken,"game1");
        CreateGameResult createGameResult = new CreateGamesService(createGameRequest).createGame(authToken,createGameRequest);
        ListGamesRequest request = new ListGamesRequest(authToken);
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