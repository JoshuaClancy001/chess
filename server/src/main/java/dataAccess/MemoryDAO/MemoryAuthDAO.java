package dataAccess.MemoryDAO;

import dataAccess.AuthDAO;

import java.util.ArrayList;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO {

    private ArrayList<String> authTokens = new ArrayList<>();
    @Override
    public void createAuth() {
        String authToken = UUID.randomUUID().toString();
        authTokens.add(authToken);
    }

    @Override
    public void readAuth() {

    }

    @Override
    public void updateAuth() {

    }

    @Override
    public void deleteAuth() {

    }

    @Override
    public void clearAuths() {
        authTokens.clear();
    }
}
