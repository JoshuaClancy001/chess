package dataAccessTests;

import dataAccess.SQLDAO.SQLUSERDAO;
import Request.RegisterRequest;
import Result.RegisterResult;
import dataAccess.DataAccessException;
import model.UserData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import service.*;

import java.util.UUID;

class RegistrationServiceTest extends Services {


    @BeforeEach
    void setUp() throws DataAccessException {
        // Initialize or reset any necessary state before each test
        userDao = new SQLUSERDAO();
    }

    @Test
    void createUserSuccess() throws DataAccessException {
        SQLUSERDAO dao = new SQLUSERDAO();
        dao.clearUsers();
        String expected = "username";
        dao.createUser(new UserData("username","password","email"));
        String actual = dao.readUser("username","password");

        Assertions.assertEquals(expected,actual);
    }

    @Test
    void createUserFailure() throws DataAccessException{
        RegisterRequest request = new RegisterRequest("username",null,"email");

        Assertions.assertThrows(DataAccessException.class,() -> new RegistrationService(request).createUser(request));

    }



    @Test
    void registerSuccess() throws DataAccessException {

        RegisterRequest request = new RegisterRequest("username","1","1.2");

        String authToken = UUID.randomUUID().toString();
        RegisterResult expected = new RegisterResult("username",authToken);

        RegisterResult actual = new RegistrationService(request).register(request);

        Assertions.assertEquals(expected.username(),actual.username());;

        request = null;
        expected = null;
        actual = null;

    }
    //username taken
    @Test
    void registerFail() throws DataAccessException {

        RegisterRequest request = new RegisterRequest("username","1","1.2");

        String authToken = UUID.randomUUID().toString();

        Assertions.assertThrows(DataAccessException.class,() -> new RegistrationService(request).register(request));

    }
    @AfterAll
    static void tearDown() throws DataAccessException {
        new ClearApplicationService().clearApplication();
    }
}