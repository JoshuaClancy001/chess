package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryDAO.MemoryAuthDAO;
import dataAccess.MemoryDAO.MemoryGameDAO;
import dataAccess.MemoryDAO.MemoryUserDAO;
import dataAccess.SQLDAO.SQLAUTHDAO;
import dataAccess.SQLDAO.SQLUSERDAO;
import model.AuthData;
import model.GameData;

import java.util.ArrayList;

public class Services {
    static protected SQLUSERDAO userDao;
    static protected SQLAUTHDAO authDao;

    static {
        try {
            userDao = new SQLUSERDAO();
            authDao = new SQLAUTHDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
    static protected MemoryGameDAO gameDao = new MemoryGameDAO();

    public String createAuth(String username) throws DataAccessException {
        String auth = authDao.createAuth(username);
        return auth;
    }

    public void addPlayer(String clientColor,String username, int gameID) throws DataAccessException {
        gameDao.updateGame(clientColor,username,gameID);
    }

    public GameData addGame(String gameName){
        return gameDao.addGame(gameName);
    }

    public ArrayList<GameData> listGames(){
        return gameDao.readGame();
    }

    public String getUser(String username,String password) throws DataAccessException {
        return userDao.readUser(username,password);
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        return authDao.readAuth(authToken);
    }

    public void deleteAuth(String authToken)throws DataAccessException {
        authDao.deleteAuth(authToken);
    }
}
