package dataAccess.SQLDAO;

import dataAccess.DAO.AuthDAO;
import dataAccess.DataAccessException;
import model.AuthData;

public class SQLAUTHDAO implements AuthDAO {
    @Override
    public String createAuth(String username) {
        return null;
    }

    @Override
    public AuthData readAuth(String authToken) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {

    }

    @Override
    public void clearAuths() {

    }
}
