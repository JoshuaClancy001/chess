package passoffTests.serviceTests;

import dataAccess.DataAccessException;
import dataAccess.SQLDAO.SQLAUTHDAO;
import dataAccess.SQLDAO.SQLGAMEDAO;
import model.GameData;
import server.Request.RegisterRequest;
import dataAccess.MemoryDAO.MemoryAuthDAO;
import model.AuthData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.ClearApplicationService;
import service.Services;

import java.util.ArrayList;
import java.util.UUID;

class ClearApplicationServiceTest extends Services {

    String authToken = UUID.randomUUID().toString();

    @Test
    void clearApplication() throws DataAccessException {
        gameDao = new SQLGAMEDAO();
        RegisterRequest request = new RegisterRequest("username","password","email");
        ArrayList<GameData> expected = new ArrayList<>();
        gameDao.addGame("game1");
        new ClearApplicationService().clearApplication();
        ArrayList<GameData> actual = gameDao.readGame();



        Assertions.assertEquals(expected,actual);
    }
}