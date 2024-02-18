package dataAccess.MemoryDAO;

import dataAccess.GameDAO;
import model.GameData;

import java.util.ArrayList;

public class MemoryGameDAO implements GameDAO {
    private ArrayList<GameData> games = new ArrayList<>();
    @Override
    public void createGame() {

    }

    @Override
    public void readGame() {

    }

    @Override
    public void updateGame() {

    }

    @Override
    public void deleteGame() {

    }

    @Override
    public void clearGames() {
        games.clear();
    }
}
