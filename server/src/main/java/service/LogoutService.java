package service;

import dataAccess.DataAccessException;
import model.AuthData;
import server.Request.LogoutRequest;
import server.Result.LogoutResult;

public class LogoutService extends Services {

    private LogoutRequest logout;

    public LogoutService(LogoutRequest logout) {
        this.logout = logout;
    }
    public LogoutResult logout(LogoutRequest logoutRequest, String authToken) throws DataAccessException{

        AuthData data = getAuth(authToken);

        if (data == null){
            throw new DataAccessException("Unauthorized");
        }

        else {
            deleteAuth(authToken);
        }

        return new LogoutResult();
    }

}
