package dataAccess.SQLDAO;

import dataAccess.DAO.UserDAO;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import model.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
            CREATE TABLE IF NOT EXISTS  USERDATA (
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
        String sql = "INSERT INTO USERDATA (username, password, email) VALUES (?, ?, ?)";
        try (var connection = DatabaseManager.getConnection()) {
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {

                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String hashedPassword = encoder.encode(player.password());

                stmt.setString(1, player.username());
                stmt.setString(2, hashedPassword);
                stmt.setString(3, player.email());

                if (stmt.executeUpdate() == 1) {
                    //System.out.println("Updated user: " + player.username());
                } else {
                    //System.out.println("Failed to update user " + player.username());
                }
            }
        } catch (SQLException ex) {
            // ERROR
        }

    }

    @Override
    public String readUser(String username, String password) throws DataAccessException {
        String sql = "select * from userData where username = ?";
        try (var connection = DatabaseManager.getConnection()) {
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, username);
                ResultSet resultSet = stmt.executeQuery();

                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

                if (resultSet.next()) {
                    if (!encoder.matches(password,resultSet.getString("password"))) {
                        return "Wrong Password";
                    }
                    return resultSet.getString("username"); // Return the username if found
                } else {
                    return null; // Return null if the username is not found
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("read user fail");
        }
    }

    @Override
    public void clearUsers()throws DataAccessException{
        String sql = "truncate userData";
        try (var connection = DatabaseManager.getConnection()) {
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.execute();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}