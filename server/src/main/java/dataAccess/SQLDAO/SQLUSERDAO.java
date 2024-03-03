package dataAccess.SQLDAO;

import dataAccess.DAO.UserDAO;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import model.UserData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUSERDAO implements UserDAO {



    public SQLUSERDAO() throws DataAccessException {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createUser(UserData player) throws DataAccessException {
        String sql = "update userData " +
                "set username = ?, password = ?, email = ?";
    try (var connection = DatabaseManager.getConnection()) {
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, player.username());
            stmt.setString(2, player.password());
            stmt.setString(3, player.email());

            if (stmt.executeUpdate() == 1) {
                System.out.println("Updated user: " + player.username());
            }
            else {
                System.out.println(
                        "Failed to update user " + player.username());
            }
        }
        } catch(SQLException ex) {
            // ERROR
        }

    }

    @Override
    public String readUser(String username, String password) throws DataAccessException {
        String sql = "select * from userData where username = ?";
        try (var connection = DatabaseManager.getConnection()) {
            try(PreparedStatement stmt = connection.prepareStatement(sql)) {
                ResultSet resultSet = stmt.executeQuery(sql);
                if (resultSet.wasNull()){
                    return username;
                }
                else{
                    return null;
                }
            }
        } catch(SQLException ex) {
            return null;
        }
    }

    @Override
    public void clearUsers() {

    }
}
