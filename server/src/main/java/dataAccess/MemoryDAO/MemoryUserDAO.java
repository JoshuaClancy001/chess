package dataAccess.MemoryDAO;

import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import model.UserData;

import java.util.ArrayList;

public class MemoryUserDAO implements UserDAO {

    private static ArrayList<UserData> users = new ArrayList<>();

    @Override
    public void createUser(UserData player){
        users.add(player);
    }

    @Override
    public String readUser(String username){

        if (users.size() == 0){
            return "empty";
        }

        for (UserData user : users){
            if (username.equals(user.username())){
                return username;
            }
        }
        return null;
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