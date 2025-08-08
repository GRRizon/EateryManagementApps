package eaterymanagementapps;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public Connection getConnection() {
        Connection connection = null;
        try {
            // Replace with your actual MySQL credentials
            String url = "jdbc:mysql://localhost:3306/eaterybd";
            String user = "root"; // or your MySQL username
            String password = "golamrabbani"; // your MySQL password ("" if blank)

            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
        return connection;
    }
}
