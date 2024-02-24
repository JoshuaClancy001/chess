package service;

import server.Request.RegisterRequest;
import server.Result.RegisterResult;
import dataAccess.DataAccessException;
import model.UserData;

public class RegistrationService extends Services{
    private RegisterRequest register;

    public RegistrationService(RegisterRequest request) {
        this.register = request;
    }

    public RegisterResult register(RegisterRequest registerRequest) throws DataAccessException {
        createUser(registerRequest);
        String authToken = createAuth(registerRequest.username());
        return new RegisterResult(registerRequest.username(),authToken);
    }
    public void createUser(RegisterRequest register)throws DataAccessException{
        UserData user = new UserData(register.username(), register.password(), register.email());

        if ((user.password() == null)){
            throw new DataAccessException("bad request");
        }

        if (getUser(user.username(),user.password()) == null){
            userDao.createUser(user);
        }

        else if (getUser((user.username()),user.password()).equals(register.username())){
            throw new DataAccessException("User already exists");
        }
        else {
            userDao.createUser(user);
        }
    }
}
