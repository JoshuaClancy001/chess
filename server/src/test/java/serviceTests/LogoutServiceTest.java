package serviceTests;

import server.Request.LogoutRequest;
import server.Request.RegisterRequest;
import server.Result.LogoutResult;
import dataAccess.DataAccessException;
import dataAccess.MemoryDAO.MemoryAuthDAO;
import model.AuthData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearApplicationService;
import service.LogoutService;
import service.Services;

import java.util.ArrayList;
import java.util.UUID;

class LogoutServiceTest extends Services {

    String authToken = UUID.randomUUID().toString();
    @BeforeEach
    void setUp() throws DataAccessException {
        authDAO  = new MemoryAuthDAO();
        RegisterRequest request = new RegisterRequest("username","password","email");
        authDAO.addAuth(authToken,request.username());

    }

    @Test
    void logoutSuccess() throws DataAccessException {
        LogoutRequest request = new LogoutRequest(authToken);
        ArrayList<AuthData> expected = new ArrayList<>();
        LogoutResult result = new LogoutService(request).logout(request,authToken);
        ArrayList<AuthData> actual = new ArrayList<>();
        actual = authDAO.getUsers();

        Assertions.assertEquals(expected,actual);
    }

    //wrong authToken
    @Test
    void logoutFailure() throws DataAccessException {
        LogoutRequest request = new LogoutRequest("wrong");

        Assertions.assertThrows(DataAccessException.class,()-> new LogoutService(request).logout(request,"wrong"));

    }

    @AfterAll
    static void tearDown(){
        new ClearApplicationService().clearApplication();
    }
}