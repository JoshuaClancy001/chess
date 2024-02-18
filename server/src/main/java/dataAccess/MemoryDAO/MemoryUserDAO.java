package dataAccess.MemoryDAO;

import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import model.UserData;

import java.util.ArrayList;

public class MemoryUserDAO implements UserDAO {

    private ArrayList<UserData> users = new ArrayList<>();

    @Override
    public void createUser(UserData player) throws DataAccessException {
        users.add(player);
    }

    @Override
    public String readUser(String username){
        for (UserData user : users){
            if (username.equals(user.username())){
                return null;
            }
        }
        return username;
    }

    @Override
    public void updateUser() throws DataAccessException {

    }

    @Override
    public void deleteUser(){

    }

    @Override
    public void clearUsers() {
        users.clear();
    }
}
