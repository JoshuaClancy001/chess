package serviceTests;

import Request.ListGamesRequest;
import Request.RegisterRequest;
import Result.ListGamesResult;
import Result.RegisterResult;
import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.MemoryDAO.MemoryAuthDAO;
import model.GameData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearApplicationService;
import service.ListGamesService;
import service.LogoutService;
import service.Services;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ListGamesServiceTest extends Services {

    String authToken = UUID.randomUUID().toString();
    @BeforeEach
    void setUp() throws DataAccessException {
        authDAO  = new MemoryAuthDAO();
        RegisterRequest request = new RegisterRequest("username","password","email");
        authDAO.addAuth(authToken,request.username());

    }

    @Test
    void listGamesSuccess() throws DataAccessException {
        ListGamesRequest request = new ListGamesRequest(authToken);
        gameDAO.addGame("game1");
        ArrayList<GameData> expected = gameDAO.readGame();
        ListGamesResult result = new ListGamesService(request).listGames(authToken,request);

        Assertions.assertEquals(expected,result.games());

    }

    @Test
    void listGamesFailure(){
        ListGamesRequest request = new ListGamesRequest("wrong");

        Assertions.assertThrows(DataAccessException.class,()-> new ListGamesService(request).listGames("wrong",request));


    }

    @AfterAll
    static void tearDown(){
        new ClearApplicationService().clearApplication();
    }
}