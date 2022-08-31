package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NUserDao extends UserDao {
    public static final String COM_MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";

    @Override
    protected Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(COM_MYSQL_JDBC_DRIVER);
        return DriverManager.getConnection("jdbc:mysql://localhost/study", "uram", "qkr!dnfka12");
    }
}
