package dataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.SQLDAO.SQLAUTHDAO;
import dataAccess.SQLDAO.SQLGAMEDAO;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.Services;

public class AUTHDATASQLTESTS extends Services {

    SQLAUTHDAO dao = new SQLAUTHDAO();

    public AUTHDATASQLTESTS() throws DataAccessException {
    }

    @Test
    void positiveCreateAuth(){
        String actual;
        String expected = "No Error Thrown";
        try {
            dao.createAuth("username");
            actual = "No Error Thrown";
        } catch (DataAccessException e) {
            actual = "Error Thrown: " + e.getMessage();
        }
        Assertions.assertEquals(expected,actual);
    }

    //null username
    @Test
    void negativeCreateAuth(){
        Assertions.assertThrows(DataAccessException.class,()->dao.createAuth(null));
    }

    @Test
    void positiveReadAuth() throws DataAccessException {
        Assertions.assertDoesNotThrow(()->dao.readAuth("authToken"));
    }

    //authToken is null
    @Test
    void negativeReadAuth() throws DataAccessException {
        AuthData expected = new AuthData("auth","username");
        AuthData actual = dao.readAuth("auth");
        Assertions.assertNotEquals(expected,actual);

    }


    @Test
    void positiveDeleteAuth() throws DataAccessException {
        String authToken = dao.createAuth("username");
        Assertions.assertDoesNotThrow(()->dao.deleteAuth(authToken));
    }

    // authToken is null
    @Test
    void negativeDeleteAuth(){
        Assertions.assertThrows(DataAccessException.class,()->dao.deleteAuth(null));
    }

    @Test
    void positiveClearAuths() throws DataAccessException {
        String expected = null;
        dao.createAuth("username");
        dao.clearAuths();
        AuthData actual = dao.readAuth("auth");

        Assertions.assertEquals(expected,actual);
    }
}
