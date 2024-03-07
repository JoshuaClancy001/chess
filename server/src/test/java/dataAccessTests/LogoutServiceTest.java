package dataAccessTests;

import dataAccess.SQLDAO.SQLAUTHDAO;
import org.junit.jupiter.api.*;
import server.Request.LoginRequest;
import server.Request.LogoutRequest;
import server.Request.RegisterRequest;
import server.Result.LoginResult;
import server.Result.LogoutResult;
import dataAccess.DataAccessException;
import server.Result.RegisterResult;
import service.*;

import java.util.UUID;

class LogoutServiceTest extends Services {

    String authToken = UUID.randomUUID().toString();
    @BeforeEach
    void setUp() throws DataAccessException {
        authDao  = new SQLAUTHDAO();
        RegisterRequest request = new RegisterRequest("username","password","email");
        RegisterResult registerResult = new RegistrationService(request).register(request);
        LoginRequest loginRequest = new LoginRequest("username","password");
        LoginResult loginResult = new LoginService(loginRequest).login(loginRequest);
        authToken = loginResult.authToken();

    }

    @Test
    void logoutSuccess() throws DataAccessException {
        LogoutRequest request = new LogoutRequest(authToken);
        LogoutResult expected = new LogoutResult();
        LogoutResult actual = new LogoutService(request).logout(request,authToken);

        Assertions.assertEquals(expected,actual);
    }

    //wrong authToken
    @Test
    void logoutFailure() throws DataAccessException {
        LogoutRequest request = new LogoutRequest("wrong");

        Assertions.assertThrows(DataAccessException.class,()-> new LogoutService(request).logout(request,"wrong"));

    }

    @AfterEach
    void tearDown() throws DataAccessException {
        new ClearApplicationService().clearApplication();
    }
}