package dataAccess;

import model.AuthData;

public interface AuthDAO{

    String createAuth(String username);

    AuthData readAuth(String authToken)throws DataAccessException;

    public void updateAuth()throws DataAccessException;

    void deleteAuth(String authToken);

    public void clearAuths();
}
