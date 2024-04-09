package dataAccess.MemoryDAO;

import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.DAO.GameDAO;
import model.GameData;

import java.util.ArrayList;

public class MemoryGameDAO implements GameDAO {
    private static ArrayList<GameData> games = new ArrayList<>();

    private int gameID = 1;
    @Override
    public GameData addGame(String gameName) {

        GameData game = new GameData(gameID,null,null,gameName,new ChessGame());
        gameID += 1;
        games.add(game);

        return game;
    }

    @Override
    public ArrayList<GameData> readGame() {
        return games;
    }

    @Override
    public void updateGame(String clientColor,String username, int gameID) throws DataAccessException {
        boolean isValidGame = false;
        for (GameData data : games){
            if (data.getGameID() == gameID) {
                isValidGame = true;
                if (clientColor != null) {
                    if (clientColor.equals("WHITE")) {
                        if (data.getWhiteUsername() != null) {
                            throw new DataAccessException(403,"already taken");
                        }
                        data.setWhiteUsername(username);
                    } else if (clientColor.equals("BLACK")) {
                        if (data.getBlackUsername() != null) {
                            throw new DataAccessException(403,"already taken");
                        }
                        data.setBlackUsername(username);
                    }

                }
                return;
            }

        }

        if (isValidGame == false){
            throw new DataAccessException(401,"no gameID");
        }


    }

    @Override
    public void clearGames() {
        games.clear();
    }
}
