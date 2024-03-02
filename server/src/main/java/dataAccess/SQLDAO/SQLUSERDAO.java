package dataAccess.SQLDAO;

import dataAccess.DAO.UserDAO;
import dataAccess.DataAccessException;
import model.UserData;

public class SQLUSERDAO implements UserDAO {
    @Override
    public void createUser(UserData player) throws DataAccessException {

    }

    @Override
    public String readUser(String username, String password) throws DataAccessException {
        return null;
    }

    @Override
    public void clearUsers() {

    }
}
