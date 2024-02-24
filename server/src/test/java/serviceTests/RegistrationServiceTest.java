package serviceTests;

import Request.RegisterRequest;
import Result.RegisterResult;
import dataAccess.DataAccessException;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import service.RegistrationService;
import service.Services;
import dataAccess.MemoryDAO.MemoryUserDAO;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceTest extends Services {


    @BeforeEach
    void setUp() {
        // Initialize or reset any necessary state before each test
        userDao = new MemoryUserDAO();
    }

    @Test
    void createUserSuccess() throws DataAccessException {
        userDao.getUsers().clear();
        ArrayList<UserData> expected = new ArrayList<>();
        ArrayList<UserData> actual = userDao.getUsers();
        RegisterRequest request = new RegisterRequest("username","password","email");

        UserData user = new UserData("username","password","email");
        expected.add(user);

        new RegistrationService(request).createUser(request);

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
}