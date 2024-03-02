package dataAccess.DAO;

import dataAccess.DataAccessException;
import model.GameData;

import java.util.ArrayList;

public interface GameDAO{

    GameData addGame(String gameName);

    public ArrayList<GameData> readGame()throws DataAccessException;

    public void updateGame(String clientColor,String username,int gameID)throws DataAccessException;

    public void clearGames();
}
