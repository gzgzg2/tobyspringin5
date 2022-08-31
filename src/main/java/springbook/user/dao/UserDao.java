package springbook.user.dao;

import springbook.user.domain.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class UserDao {

    public static final String COM_MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = getConnection();

        PreparedStatement preparedStatement = c.prepareStatement("insert into USERS(id, Name, Password) values(?,?,?) ");

        preparedStatement.setString(1, user.getId());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setString(3, user.getPassword());

        preparedStatement.executeUpdate();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection c = getConnection();
        PreparedStatement preparedStatement = c.prepareStatement("SELECT * FROM USERS WHERE id = ?");


        preparedStatement.setString(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        User user = new User();
        user.setId(resultSet.getString("id"));
        user.setName(resultSet.getString("name"));
        user.setPassword(resultSet.getString("password"));


        resultSet.close();
        preparedStatement.close();
        c.close();
        return user;
    }

    protected abstract  Connection getConnection() throws ClassNotFoundException, SQLException;
}
