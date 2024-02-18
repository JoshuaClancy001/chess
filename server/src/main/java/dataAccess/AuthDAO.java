package dataAccess;

public interface AuthDAO{
    public void createAuth()throws DataAccessException;

    public void readAuth()throws DataAccessException;

    public void updateAuth()throws DataAccessException;

    public void deleteAuth()throws DataAccessException;

    public void clearAuths();
}
