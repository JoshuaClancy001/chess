package dataAccess.MemoryDAO;

import dataAccess.DAO.AuthDAO;
import dataAccess.DataAccessException;
import model.AuthData;

import java.util.ArrayList;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO {

    private static ArrayList<AuthData> authTokens = new ArrayList<>();


    @Override
    public String createAuth(String username) {
        String authToken = UUID.randomUUID().toString();

        AuthData authData = new AuthData(authToken,username);

        authTokens.add(authData);

        return authToken;
    }

    @Override
    public AuthData readAuth(String authToken)throws DataAccessException {
        boolean isValidAuthToken = false;
        int index = 0;
        for (AuthData data : authTokens){
            if (data.authToken().equals(authToken)){
                return data;
            }
            index += 1;
        }
        if (!isValidAuthToken) {
            return null;
        }
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        for (int i = 0; i < authTokens.size(); i++){
            if (authTokens.get(i).authToken().equals(authToken)){
                authTokens.remove(i);
                return;
            }
        }
        throw new DataAccessException(1,"AuthToken Not There");
    }

    @Override
    public void clearAuths() {
        authTokens.clear();
    }
}
