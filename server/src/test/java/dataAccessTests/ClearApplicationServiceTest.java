package dataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.SQLDAO.SQLGAMEDAO;
import model.GameData;
import Request.RegisterRequest;
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