package service;

import dataAccess.DataAccessException;

public class ClearApplicationService extends Services {

    public ClearApplicationService(){
    }

    public void clearApplication() {
        userDao.clearUsers();
        authDao.clearAuths();
        gameDao.clearGames();
    }
}
