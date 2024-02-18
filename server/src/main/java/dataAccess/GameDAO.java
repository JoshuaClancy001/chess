package dataAccess;

public interface GameDAO{
    public void createGame()throws DataAccessException;

    public void readGame()throws DataAccessException;

    public void updateGame()throws DataAccessException;

    public void deleteGame()throws DataAccessException;

    public void clearGames();
}
