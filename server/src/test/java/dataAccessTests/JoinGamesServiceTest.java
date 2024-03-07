package dataAccessTests;

import server.Request.CreateGameRequest;
import server.Request.JoinGameRequest;
import server.Request.LoginRequest;
import server.Request.RegisterRequest;
import server.Result.CreateGameResult;
import server.Result.JoinGameResult;
import dataAccess.DataAccessException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Result.LoginResult;
import server.Result.RegisterResult;
import service.*;

import java.util.UUID;

class JoinGamesServiceTest extends Services {
    String authToken = UUID.randomUUID().toString();
    int gameID;
    @BeforeEach
    void setUp() throws DataAccessException {
        new ClearApplicationService().clearApplication();
        RegisterRequest request = new RegisterRequest("username","password","email");
        RegisterResult registerResult = new RegistrationService(request).register(request);
        LoginRequest loginRequest = new LoginRequest("username","password");
        LoginResult loginResult = new LoginService(loginRequest).login(loginRequest);
        authToken = loginResult.authToken();
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
    void joinGameFailure() throws DataAccessException {
        JoinGameRequest request = new JoinGameRequest("Wrong","WHITE",gameID);

        //GameData game = new SQLGAMEDAO().readOneGame(gameID);

        Assertions.assertThrows(DataAccessException.class,()-> new JoinGamesService(request).joinGame("wrong",request));
    }

    @AfterAll
    static void tearDown() throws DataAccessException {
        new ClearApplicationService().clearApplication();
    }
}