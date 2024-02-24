package dataAccess;

import model.GameData;

import java.util.ArrayList;

public interface GameDAO{

    GameData addGame(String gameName);

    public ArrayList<GameData> readGame()throws DataAccessException;

    public void updateGame(String clientColor,String username,int gameID)throws DataAccessException;

    public void deleteGame()throws DataAccessException;

    public void clearGames();
}
