package serviceTests;

import Request.LoginRequest;
import Request.RegisterRequest;
import Result.ClearApplicationResult;
import dataAccess.MemoryDAO.MemoryAuthDAO;
import model.AuthData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.ClearApplicationService;
import service.LogoutService;
import service.Services;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ClearApplicationServiceTest extends Services {

    String authToken = UUID.randomUUID().toString();

    @Test
    void clearApplication() {
        authDAO  = new MemoryAuthDAO();
        RegisterRequest request = new RegisterRequest("username","password","email");
        ArrayList<AuthData> expected = new ArrayList<>();
        authDAO.addAuth(authToken,request.username());
        new ClearApplicationService().clearApplication();
       ArrayList<AuthData> actual = authDAO.getUsers();

        Assertions.assertEquals(expected,actual);
    }
}