package dataAccess.MemoryDAO;

import dataAccess.AuthDAO;
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

        for (AuthData data : authTokens){
            if (data.authToken().equals(authToken)){
                return data;
            }
        }
        return null;
    }

    @Override
    public void updateAuth() {

    }

    @Override
    public void deleteAuth(String authToken) {
        for (int i = 0; i < authTokens.size(); i++){
            if (authTokens.get(i).authToken().equals(authToken)){
                authTokens.remove(i);
            }
        }
    }

    @Override
    public void clearAuths() {
        authTokens.clear();
    }
}
