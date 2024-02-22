package service;

import dataAccess.DataAccessException;
import service.Request.LoginRequest;
import service.Request.LogoutRequest;
import service.Result.LoginResult;

public class LoginService extends  Services{
    private LoginRequest login;

    public LoginService(LoginRequest login) {
        this.login = login;
    }
    public LoginResult login(LoginRequest loginRequest) throws DataAccessException{

        String user = getUser(loginRequest.username());

        if (user != null && !user.equals("empty")){
            String authToken = createAuth();
            return new LoginResult(loginRequest.username(),authToken);

        }
        else{
            throw new DataAccessException("User not Registered");
        }

    }
    public String getUser(String username){
        return userDao.readUser(username);
    }
}
