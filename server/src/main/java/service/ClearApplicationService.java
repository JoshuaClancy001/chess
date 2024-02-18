package service;

public class ClearApplicationService extends Services {

    public void clearApplication(){
        userDao.clearUsers();
        authDAO.clearAuths();
        gameDAO.clearGames();
    }
}
