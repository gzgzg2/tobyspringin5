package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnectionMaker implements ConnectionMaker {
    public static final String COM_MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";

    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        Class.forName(COM_MYSQL_JDBC_DRIVER);
        return DriverManager.getConnection("jdbc:mysql://localhost/study", "uram", "qkr!dnfka12");
    }
}
