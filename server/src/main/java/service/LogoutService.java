package service;

import service.Request.LogoutRequest;
import service.Request.RegisterRequest;

public class LogoutService {

    private LogoutRequest logout;

    public LogoutService(LogoutRequest logout) {
        this.logout = logout;
    }
    public void logout(LogoutRequest logoutRequest){}
}
