package springbook.user.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void add(User user) throws SQLException {
        class AddStatement implements StatementStrategy {

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

        StatementStrategy statementStrategy = new AddStatement();
        jdbcContextWithStatementStrategy(statementStrategy);
    }

    public User get(String id) throws SQLException {
        try (Connection c = dataSource.getConnection();
             PreparedStatement preparedStatement = c.prepareStatement("SELECT * FROM USERS WHERE id = ?"))
        {
            preparedStatement.setString(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {

                User user = null;

                if (resultSet.next()) {
                    user = new User(
                            resultSet.getString("id"),
                            resultSet.getString("name"),
                            resultSet.getString("password")
                    );
                }

                if (user == null) throw new EmptyResultDataAccessException(1);

                return user;
            }
        }
    }

    public void deleteAll() throws SQLException {
        StatementStrategy statementStrategy = new DeleteAllStatement();
        jdbcContextWithStatementStrategy(statementStrategy);
    }

    public int getCount() throws SQLException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("SELECT count(*) FROM USERS");

        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        rs.close();
        ps.close();
        c.close();

        return count;
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy statementStrategy) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = dataSource.getConnection();

            PreparedStatement preparedStatement = statementStrategy.makePreparedStatement(c);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {}
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {}
            }
        }
    }

}
