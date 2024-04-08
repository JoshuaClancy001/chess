package dataAccessTests;

import org.junit.jupiter.api.BeforeEach;
import Request.LoginRequest;
import Request.RegisterRequest;
import Result.LoginResult;
import Result.RegisterResult;
import dataAccess.DataAccessException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.ClearApplicationService;
import service.LoginService;
import service.RegistrationService;

import java.util.UUID;

class LoginServiceTest {

    @BeforeEach
    void setUp() throws DataAccessException {
        new ClearApplicationService().clearApplication();
    }

    @Test
    void loginSuccess() throws DataAccessException {
        RegisterRequest registerRequest = new RegisterRequest("username","password","email");
        RegisterResult registerResult = new RegistrationService(registerRequest).register(registerRequest);
        LoginRequest request = new LoginRequest("username","password");
        String authToken = UUID.randomUUID().toString();
        LoginResult expected = new LoginResult("username",authToken);
        LoginResult actual = new LoginService(request).login(request);
        Assertions.assertEquals(expected.username(),actual.username());
    }

    // wrong password
    @Test
    void loginFail() throws DataAccessException {
        RegisterRequest request = new RegisterRequest("username","password","email");
        RegisterResult result = new RegistrationService(request).register(request);
        LoginRequest loginRequest = new LoginRequest("username","wrong");
        Assertions.assertThrows(DataAccessException.class,() ->new LoginService(loginRequest).login(loginRequest));
    }

    @AfterAll
    static void tearDown() throws DataAccessException {
        new ClearApplicationService().clearApplication();
    }
}