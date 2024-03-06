package passoffTests.serviceTests;

import chess.*;
import dataAccess.DataAccessException;
import dataAccess.SQLDAO.SQLAUTHDAO;
import dataAccess.SQLDAO.SQLGAMEDAO;
import org.junit.jupiter.api.BeforeEach;
import server.Request.CreateGameRequest;
import server.Request.JoinGameRequest;
import server.Request.LoginRequest;
import server.Request.RegisterRequest;
import dataAccess.MemoryDAO.MemoryAuthDAO;
import model.AuthData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import server.Result.CreateGameResult;
import server.Result.JoinGameResult;
import server.Result.LoginResult;
import server.Result.RegisterResult;
import service.*;

import java.util.ArrayList;
import java.util.UUID;

class ChessGameStoreTests extends Services {

    String authToken = UUID.randomUUID().toString();

    @BeforeEach
    void setUP() throws DataAccessException {
        new ClearApplicationService().clearApplication();
        RegisterRequest request = new RegisterRequest("username","password","email");
        RegisterResult registerResult = new RegistrationService(request).register(request);
        LoginRequest loginRequest = new LoginRequest("username","password");
        LoginResult loginResult = new LoginService(loginRequest).login(loginRequest);
        authToken = loginResult.authToken();
    }

    @Test
    void StoreInitialGame() throws DataAccessException{
        SQLGAMEDAO gameDao = new SQLGAMEDAO();
        ChessGame expected = new ChessGame();
        CreateGameRequest createGameRequest = new CreateGameRequest(authToken,"game1");
        CreateGameResult createGameResult = new CreateGamesService(createGameRequest).createGame(authToken,createGameRequest);
        JoinGameRequest joinGameRequest = new JoinGameRequest(authToken,"WHITE",1);
        ChessGame actual = gameDao.readOneGame(1).getGame();

        Assertions.assertEquals(expected,actual);
    }

    @Test
    void StoreNewMove() throws DataAccessException, InvalidMoveException {
        SQLGAMEDAO gameDao = new SQLGAMEDAO();
        ChessGame expectedG = new ChessGame();
        expectedG.setTeamTurn(ChessGame.TeamColor.WHITE);
        ChessGame actualGame = new ChessGame();
        actualGame.setTeamTurn(ChessGame.TeamColor.WHITE);
        expectedG.makeMove(new ChessMove(new ChessPosition(2,1),new ChessPosition(3,1),null));
        ChessBoard expected = expectedG.getBoard();
        CreateGameRequest createGameRequest = new CreateGameRequest(authToken,"game1");
        CreateGameResult createGameResult = new CreateGamesService(createGameRequest).createGame(authToken,createGameRequest);
        JoinGameRequest joinGameRequest = new JoinGameRequest(authToken,"WHITE",1);
        actualGame.makeMove(new ChessMove(new ChessPosition(2,1),new ChessPosition(3,1),null));
        gameDao.updateChessGame(1,actualGame);
        ChessBoard actual = gameDao.readOneGame(1).getGame().getBoard();

        Assertions.assertEquals(expected,actual);
    }
}