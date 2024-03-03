package dataAccess.SQLDAO;

import dataAccess.DAO.GameDAO;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import model.GameData;

import java.sql.SQLException;
import java.util.ArrayList;

public class SQLGAMEDAO implements GameDAO {


    public SQLGAMEDAO() throws DataAccessException {
        configureDatabase();
    }


    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  USERDAO (
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL,
              PRIMARY KEY (`username`)
            )
            """
    };

//    ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }



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
