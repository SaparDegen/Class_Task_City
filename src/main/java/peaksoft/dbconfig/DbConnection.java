package peaksoft.dbconfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static final String userName = "postgres";
    private static final String password = "1234";
    private static final String url = "jdbc:postgresql://localhost:5432/classTask_country";

    public static Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, userName, password);
            //System.out.println("Connection was created successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return connection;
    }
}
