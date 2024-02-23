package service;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.MemoryDAO.MemoryAuthDAO;
import dataAccess.MemoryDAO.MemoryGameDAO;
import dataAccess.MemoryDAO.MemoryUserDAO;
import model.AuthData;

import java.util.UUID;

public class Services {
    static protected MemoryUserDAO userDao = new MemoryUserDAO();
    static protected MemoryAuthDAO authDAO = new MemoryAuthDAO();
    static protected MemoryGameDAO gameDAO = new MemoryGameDAO();

    public String createAuth(String username){
        String auth = authDAO.createAuth(username);

        return auth;
    }

    public String getUser(String username,String password){
        return userDao.readUser(username,password);
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        return authDAO.readAuth(authToken);
    }

    public void deleteAuth(String authToken){
        authDAO.deleteAuth(authToken);
    }
}
