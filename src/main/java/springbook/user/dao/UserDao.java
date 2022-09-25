package springbook.user.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao {

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
       this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(User user) {
        this.jdbcTemplate.update("insert into USERS(id, name, password) values(?,?,?)",
                user.getId(),
                user.getName(),
                user.getPassword());
    }

    public User get(String id){
        return this.jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE id = ?",
                (rs, rowNum) -> {
            User user = null;

            user = new User(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("password")
            );

            return user;
        }, id);
    }

    public void deleteAll(){
        this.jdbcTemplate.update("delete from users");
    }

    public int getCount() {
        return this.jdbcTemplate.queryForObject("SELECT count(*) FROM USERS", Integer.class);
    }


    public List<User> getAll() {
        return this.jdbcTemplate.query("select * from users order by id", (rs, rowNum) -> {
            User user = null;

            user = new User(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("password")
            );

            return user;
        });
    }
}
