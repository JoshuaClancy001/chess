package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryDAO.MemoryAuthDAO;
import dataAccess.MemoryDAO.MemoryGameDAO;
import dataAccess.MemoryDAO.MemoryUserDAO;
import model.AuthData;
import model.GameData;

import java.util.ArrayList;

public class Services {
    static protected MemoryUserDAO userDao = new MemoryUserDAO();
    static protected MemoryAuthDAO authDAO = new MemoryAuthDAO();
    static protected MemoryGameDAO gameDAO = new MemoryGameDAO();

    public String createAuth(String username){
        String auth = authDAO.createAuth(username);
        return auth;
    }

    public void addPlayer(String clientColor,String username, int gameID) throws DataAccessException {
        gameDAO.updateGame(clientColor,username,gameID);
    }

    public GameData addGame(String gameName){
        return gameDAO.addGame(gameName);
    }

    public ArrayList<GameData> listGames(){
        return gameDAO.readGame();
    }

    public String getUser(String username,String password){
        return userDao.readUser(username,password);
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        return authDAO.readAuth(authToken);
    }

    public void deleteAuth(String authToken)throws DataAccessException {
        authDAO.deleteAuth(authToken);
    }
}
