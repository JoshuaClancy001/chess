package service;

import service.Request.RegisterRequest;
import service.Result.RegisterResult;
import dataAccess.DataAccessException;
import model.UserData;

import java.util.UUID;

public class RegistrationService extends Services{



    private RegisterRequest register;

    public RegistrationService(RegisterRequest request) {
        this.register = request;
    }

    public RegisterResult register(RegisterRequest registerRequest) throws DataAccessException {
        createUser(registerRequest);
        String authToken = createAuth();
        return new RegisterResult(authToken);
    }

    public String getUser(String username){
        return userDao.readUser(username);
    }

    public void createUser(RegisterRequest register)throws DataAccessException{
        UserData user = new UserData(register.username(), register.password(), register.email());

        if (getUser(user.username()) == null){
            userDao.createUser(user);
        }

        else if (getUser(user.username()).equals(register.username())){
            throw new DataAccessException("User already exists");
        }
    }
}
