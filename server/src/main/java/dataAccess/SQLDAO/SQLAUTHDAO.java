package dataAccess.SQLDAO;

import dataAccess.DAO.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import model.AuthData;

import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLAUTHDAO implements AuthDAO {



    public SQLAUTHDAO() throws DataAccessException {
        configureDatabase();
    }


    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  authData (
              `authToken` varchar(256) NOT NULL,
              `username` varchar(256) NOT NULL,
              PRIMARY KEY (`authToken`)
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
            throw new DataAccessException(500, String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }
    @Override
    public String createAuth(String username) throws DataAccessException {
        String authToken = UUID.randomUUID().toString();
        String sql = "INSERT INTO authData (authToken, username) VALUES (?, ?)";
        try (var connection = DatabaseManager.getConnection()){
            try (PreparedStatement stmt = connection.prepareStatement(sql)){
                stmt.setString(1, authToken);
                stmt.setString(2, username);

                if (stmt.executeUpdate() == 1){
                    return authToken;
                }
                else {

                }
            }
        } catch (SQLException ex){
            throw new DataAccessException(500,"fail");
        }


        return null;
    }

    @Override
    public AuthData readAuth(String authToken) throws DataAccessException {
        String sql = "select * from authData where authToken = ?";
        try(var connection = DatabaseManager.getConnection()){
            try (PreparedStatement stmt = connection.prepareStatement(sql)){
                stmt.setString(1,authToken);
                ResultSet resultSet = stmt.executeQuery();

                if (resultSet.next()){
                    return new AuthData(authToken,resultSet.getString("username"));
                }
                else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(500,"read auth fail");
        }
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        String sql = "DELETE FROM authData WHERE authToken = ?";
        try (var connection = DatabaseManager.getConnection()){
            try (PreparedStatement stmt = connection.prepareStatement(sql)){
                stmt.setString(1, authToken);
                int numRowsDeleted = stmt.executeUpdate();

                if (numRowsDeleted == 0){
                    throw new DataAccessException(500, "AuthToken Not There");
                }
            }
        } catch (SQLException ex){
            throw new DataAccessException(500,"delete auth fail");
        }
    }

    @Override
    public void clearAuths() throws DataAccessException {
        String sql = "truncate authData";
        try (var connection = DatabaseManager.getConnection()) {
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.execute();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
