package passoffTests.serviceTests;

import server.Request.CreateGameRequest;
import server.Request.JoinGameRequest;
import server.Request.RegisterRequest;
import server.Result.CreateGameResult;
import server.Result.JoinGameResult;
import dataAccess.DataAccessException;
import dataAccess.MemoryDAO.MemoryAuthDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.*;

import java.util.UUID;

class JoinGamesServiceTest extends Services {
    String authToken = UUID.randomUUID().toString();
    int gameID;
    @BeforeEach
    void setUp() throws DataAccessException {
        authDAO  = new MemoryAuthDAO();
        RegisterRequest request = new RegisterRequest("username","password","email");
        authDAO.addAuth(authToken,request.username());
        CreateGameRequest createGameRequest = new CreateGameRequest(authToken,"game1");
        CreateGameResult createGameResult = new CreateGamesService(createGameRequest).createGame(authToken,createGameRequest);
        gameID = createGameResult.gameID();
    }
    @Test
    void joinGameSuccess() throws DataAccessException {
        JoinGameRequest request = new JoinGameRequest(authToken,"WHITE",gameID);
        JoinGameResult result = new JoinGamesService(request).joinGame(authToken,request);
        int expected = gameID;
        int actual = result.gameID();

        Assertions.assertEquals(expected,actual);
    }

    @Test
    void joinGameFailure(){
        JoinGameRequest request = new JoinGameRequest("Wrong","WHITE",gameID);

        Assertions.assertThrows(DataAccessException.class,()-> new JoinGamesService(request).joinGame("wrong",request));
    }

    @AfterAll
    static void tearDown(){
        new ClearApplicationService().clearApplication();
    }
}