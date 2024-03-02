package dataAccess.DAO;

import dataAccess.DataAccessException;
import model.UserData;

public interface UserDAO {

    void createUser(UserData player) throws DataAccessException;

    String readUser(String username, String password) throws DataAccessException;

    public void clearUsers();

}
