package service;

public class ClearApplicationService extends Services {

    public void clearApplication(){
        userDao.clearUsers();
        authDao.clearAuths();
        gameDao.clearGames();
    }
}
