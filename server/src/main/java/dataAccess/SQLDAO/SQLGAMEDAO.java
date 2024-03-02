package dataAccess.SQLDAO;

import dataAccess.DAO.GameDAO;
import dataAccess.DataAccessException;
import model.GameData;

import java.util.ArrayList;

public class SQLGAMEDAO implements GameDAO {
    @Override
    public GameData addGame(String gameName) {
        return null;
    }

    @Override
    public ArrayList<GameData> readGame() throws DataAccessException {
        return null;
    }

    @Override
    public void updateGame(String clientColor, String username, int gameID) throws DataAccessException {

    }

    @Override
    public void clearGames() {

    }
}
