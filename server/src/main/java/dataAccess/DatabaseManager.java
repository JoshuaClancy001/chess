package dataAccess;

import javax.xml.crypto.Data;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class DatabaseManager {
    private static final String databaseName;
    private static final String user;
    private static final String password;
    private static final String connectionUrl;


    static {
        try {
            try (InputStream in = DatabaseManager.class.getClassLoader().getResourceAsStream("db.properties")) {
                Properties props = new Properties();
                props.load(in);
                databaseName = props.getProperty("chess");
                user = props.getProperty("root");
                password = props.getProperty("password");

                var host = props.getProperty("localhost");
                var port = Integer.parseInt(props.getProperty("80"));
                connectionUrl = String.format("jdbc:mysql://%s:%d", host, port);

            }

        } catch (Exception ex) {
            throw new RuntimeException("unable to process db.properties. " + ex.getMessage());
        }
    }

    static void createDatabase() throws DataAccessException {
        try {
            var statement = "CREATE DATABASE IF NOT EXISTS " + databaseName;
            var conn = DriverManager.getConnection(connectionUrl, user, password);
            try (var preparedStatement = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException("SQL exception");
        }
    }

        static Connection getConnection() throws DataAccessException {
            try {
                var conn = DriverManager.getConnection(connectionUrl, user, password);
                conn.setCatalog(databaseName);
                return conn;
            } catch (SQLException e) {
                throw new DataAccessException("SQL exception");
            }
        }
}
