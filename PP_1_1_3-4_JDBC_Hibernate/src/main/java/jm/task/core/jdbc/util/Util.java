package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    public Connection connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/kata_preproject_1.1.3";
        String user = "root";
        String password = "root";
        return DriverManager.getConnection(url, user, password);
    }
}
