package dataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.SQLDAO.SQLUSERDAO;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class USERDATASQLTESTS {

    SQLUSERDAO dao = new SQLUSERDAO();

    public USERDATASQLTESTS() throws DataAccessException {
    }

    @BeforeEach
    void setUp() throws DataAccessException {
        dao.clearUsers();
    }

    //create normal user
    @Test
    void positiveCreateUser() throws DataAccessException,SQLException {
        String actual;
        String expected = "No Error Thrown";
        try {
            dao.createUser(new UserData("username","password","email"));
            actual = "No Error Thrown";
        } catch (DataAccessException e) {
            actual = "Error Thrown: " + e.getMessage();
        }
        Assertions.assertEquals(expected,actual);
    }

    // username is null
    @Test
    void negativeCreateUser() throws DataAccessException {

        Assertions.assertThrows(DataAccessException.class,()->dao.createUser(new UserData(null,"password","email")));
    }

    @Test
    void positiveReadUser() throws DataAccessException {
        String expected = "username";
        dao.createUser(new UserData("username","password","email"));
        String actual = dao.readUser("username","password");
        Assertions.assertEquals(expected,actual);

    }

    //Wrong Password
    @Test
    void negativeReadUser() throws DataAccessException {
        String expected = "username";
        dao.createUser(new UserData("username","password","email"));
        String actual = dao.readUser("username","wrong");
        Assertions.assertNotEquals(expected,actual);
    }

    @Test
    void positiveClearUsers() throws DataAccessException {
        String expected = null;
        dao.createUser(new UserData("username","password","email"));
        dao.clearUsers();
        String actual = dao.readUser("username","password");

        Assertions.assertEquals(expected,actual);

    }



}
