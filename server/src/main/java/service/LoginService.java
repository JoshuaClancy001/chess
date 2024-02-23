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

        String user = getUser(loginRequest.username(),loginRequest.password());

        if (user != null && !user.equals("empty") && !user.equals("Wrong Password")){
            String authToken = createAuth(loginRequest.username());
            return new LoginResult(loginRequest.username(),authToken);

        }
        else if (user == null){
            throw new DataAccessException("User not Registered");
        }
        else if (user.equals("Wrong Password")){
            throw new DataAccessException("Wrong Password");
        }
        else{
            throw new DataAccessException("User not Registered");
        }

    }
    public String getUser(String username, String password){
        return userDao.readUser(username,password);
    }
}
