package dataAccess.DAO;

import chess.ChessGame;
import dataAccess.DataAccessException;
import model.GameData;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.util.ArrayList;

public interface GameDAO{

    GameData addGame(String gameName) throws DataAccessException;

    public ArrayList<GameData> readGame()throws DataAccessException;

    public void updateGame(String clientColor,String username,int gameID)throws DataAccessException;

    public void clearGames() throws DataAccessException;
}
