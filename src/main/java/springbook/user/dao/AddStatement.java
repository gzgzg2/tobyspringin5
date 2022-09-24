package springbook.user.dao;

import springbook.user.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStatement implements StatementStrategy{

    private final User user;

    public AddStatement(User user) {
        this.user = user;
    }

    @Override
    public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("insert into user(id, name, password) values(?,?,?)");

        preparedStatement.setString(1, user.getId());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setString(3, user.getPassword());

        return preparedStatement;
    }
}
