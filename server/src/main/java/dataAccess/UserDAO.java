package dataAccess;

import model.UserData;

public interface UserDAO {

    void createUser(UserData player) throws DataAccessException;

    String readUser(String username) throws DataAccessException;

    public void updateUser()throws DataAccessException;

    public void deleteUser();

    public void clearUsers();

}