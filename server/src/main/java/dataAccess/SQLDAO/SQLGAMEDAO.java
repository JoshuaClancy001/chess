package dataAccess.SQLDAO;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DAO.GameDAO;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import model.GameData;

import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLGAMEDAO implements GameDAO {


    public SQLGAMEDAO() throws DataAccessException {
        configureDatabase();
    }


    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS gameData (
              `id` INT AUTO_INCREMENT PRIMARY KEY,
              `whiteUsername` varchar(256) NULL,
              `blackUsername` varchar(256) NULL,
              `gameName` varchar(256) NOT NULL,
              `chessGame` LONGTEXT NOT NULL
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
            throw new DataAccessException(500,String.format("Unable to configure database: %s"));
        }
    }



    @Override
    public GameData addGame(String gameName) throws DataAccessException{

        ChessGame chessGame = new ChessGame();
        var serializer = new Gson();
        var json = serializer.toJson(chessGame);

        String sql = "INSERT INTO gameData (whiteUsername,blackUsername,gameName,chessGame) VALUES (?,?,?,?)";
        try (var connection = DatabaseManager.getConnection()){
            try(PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                stmt.setString(1,null);
                stmt.setString(2, null);
                stmt.setString(3, gameName);
                stmt.setString(4, json);

                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected == 1){
                    ResultSet key = stmt.getGeneratedKeys();
                    if (key.next()) {
                        int gameID = key.getInt(1);
                        return new GameData(gameID, null, null, gameName, new ChessGame());
                    }
                }
            }
        } catch (SQLException ex){
            throw new DataAccessException(500,"fail");
        }
        throw new DataAccessException(400,"no gameID");
    }

    @Override
    public ArrayList<GameData> readGame() throws DataAccessException {
        ArrayList<GameData> games = new ArrayList<>();
        String sql = "SELECT * FROM gameData";
        try (var connection = DatabaseManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                int gameID = resultSet.getInt("id");
                String whiteUsername = resultSet.getString("whiteUsername");
                String blackUsername = resultSet.getString("blackUsername");
                String gameName = resultSet.getString("gameName");
                var deserializer = new Gson();
                ChessGame chessGame = deserializer.fromJson(resultSet.getString("chessGame"), ChessGame.class);
                GameData game = new GameData(gameID, whiteUsername, blackUsername, gameName, chessGame);
                games.add(game);
            }

        } catch (SQLException ex) {
            // Log or handle the exception appropriately
            throw new DataAccessException(500, "Failed to retrieve game data from database");
        }
        return games;
    }

    public GameData readOneGame(int gameID) throws DataAccessException{
        String sql = "select * from gameData where id = ?";
        try(var connection = DatabaseManager.getConnection()){
            try (PreparedStatement stmt = connection.prepareStatement(sql)){
                stmt.setInt(1,gameID);
                ResultSet resultSet = stmt.executeQuery();
                resultSet.next();
                    String whiteUsername = resultSet.getString("whiteUsername");
                    String blackUsername = resultSet.getString("blackUsername");
                    String gameName = resultSet.getString("gameName");
                    var deserializer = new Gson();
                    ChessGame chessGame = deserializer.fromJson(resultSet.getString("chessGame"), ChessGame.class);
                return new GameData(gameID, whiteUsername, blackUsername, gameName, chessGame);
            }
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        throw new DataAccessException(400, "no gameID");
    }

    @Override
    public void updateGame(String clientColor, String username, int gameID) throws DataAccessException {
        GameData game = readOneGame(gameID);
        String sql = "UPDATE gameData SET ";
        if (clientColor != null){
            if (clientColor.equals("WHITE")) {
                if (username != null) {
                    if (game.getWhiteUsername() == null || !game.getWhiteUsername().equals(username)) {
                        sql += "whiteUsername = ?";
                    } else {
                        throw new DataAccessException(403, "already taken");
                    }
                } else {
                    sql += "whiteUsername = NULL";
                }
            } else if (clientColor.equals("BLACK")) {
                if (username != null) {
                    if (game.getBlackUsername() == null || !game.getBlackUsername().equals(username)) {
                        sql += "blackUsername = ?";
                    } else {
                        throw new DataAccessException(403, "already taken");
                    }
                } else {
                    sql += "blackUsername = NULL";
                }
            }
        }
        sql += " WHERE id = ?";

        try (var connection = DatabaseManager.getConnection()){
            try (PreparedStatement stmt = connection.prepareStatement(sql)){
                if (username != null) {
                    stmt.setString(1, username);
                    stmt.setInt(2, gameID);
                } else {
                    stmt.setInt(1, gameID);
                }
                stmt.executeUpdate();
            }
        }catch (SQLException ex){
            //error
        }

    }


    public void updateChessGame(int gameID,ChessGame game) throws DataAccessException {
        var seserializer = new Gson();
        String json = seserializer.toJson(game);
        String sql = "UPDATE gameData SET chessGame = ? WHERE id = ?";

        try (var connection = DatabaseManager.getConnection()){
            try (PreparedStatement stmt = connection.prepareStatement(sql)){
                stmt.setString(1,json);
                stmt.setInt(2,gameID);

                if (stmt.executeUpdate() == 0){
                    throw new DataAccessException(400, "ID doesnt exist");
                }

            }
        }catch (SQLException ex){
            throw new DataAccessException(500,"fail");
        }

    }

    @Override
    public void clearGames() throws DataAccessException{
        String sql = "truncate gameData";
        try (var connection = DatabaseManager.getConnection()) {
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
