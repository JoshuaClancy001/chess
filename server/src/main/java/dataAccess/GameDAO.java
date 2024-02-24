package dataAccess;

import model.GameData;

import java.util.ArrayList;

public interface GameDAO{
    public void createGame()throws DataAccessException;

    public ArrayList<GameData> readGame()throws DataAccessException;

    public void updateGame()throws DataAccessException;

    public void deleteGame()throws DataAccessException;

    public void clearGames();
}
