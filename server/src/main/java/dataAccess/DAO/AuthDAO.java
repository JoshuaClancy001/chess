package dataAccess.DAO;

import dataAccess.DataAccessException;
import model.AuthData;

public interface AuthDAO{

    String createAuth(String username);

    AuthData readAuth(String authToken)throws DataAccessException;

    void deleteAuth(String authToken) throws DataAccessException;

    public void clearAuths();
}
