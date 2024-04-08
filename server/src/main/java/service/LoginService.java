package service;

import dataAccess.DataAccessException;
import Request.LoginRequest;
import Result.LoginResult;

public class LoginService extends  Services{
    private LoginRequest login;

    public LoginService(LoginRequest login) throws DataAccessException {
        this.login = login;
    }
    public LoginResult login(LoginRequest loginRequest) throws DataAccessException{

        String user = getUser(loginRequest.username(),loginRequest.password());

        if (user != null && !user.equals("empty") && !user.equals("Wrong Password")){
            String authToken = createAuth(loginRequest.username());
            return new LoginResult(loginRequest.username(),authToken);

        }
        else if (user == null){
            throw new DataAccessException(401,"User not Registered");
        }
        else if (user.equals("Wrong Password")){
            throw new DataAccessException(401,"Wrong Password");
        }
        else{
            throw new DataAccessException(401,"User not Registered");
        }

    }
}
