package springbook.user.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private JdbcContext jdbcContext;
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.jdbcContext = new JdbcContext();
        jdbcContext.setDataSource(dataSource);
        this.dataSource = dataSource;
    }

    public void add(User user) throws SQLException {
        jdbcContext.workWithStatementStrategy(connection -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("insert into USERS(id, name, password) values(?,?,?)");

            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getPassword());

            return preparedStatement;
        });
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
        executeSql("delete from users");
    }

    private void executeSql(String sql) throws SQLException {
        jdbcContext.workWithStatementStrategy(
                connection -> connection.prepareStatement(sql)
        );
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


}
