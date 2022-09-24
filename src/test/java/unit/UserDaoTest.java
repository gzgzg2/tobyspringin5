package unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.user.dao.JdbcContext;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


class UserDaoTest {

    UserDao dao;
    User 재엽정;
    User 정재엽;
    User 박우람;

    @BeforeEach
    void setUp() {
        재엽정 = new User("frankly", "재엽정", "1234");
        정재엽 = new User("frankle", "정재엽","1234" );
        박우람 = new User("uram2","우람", "123");

        SingleConnectionDataSource dataSource =
                new SingleConnectionDataSource("jdbc:mysql://localhost/study", "uram", "qkr!dnfka12", true);
        JdbcContext jdbcContext = new JdbcContext();
        jdbcContext.setDataSource(dataSource);

        dao = new UserDao();
        dao.setDataSource(dataSource);
        dao.setJdbcContext(jdbcContext);
    }

    @Test
    void addAndGet() throws SQLException, ClassNotFoundException {
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        //when
        dao.add(재엽정);
        dao.add(정재엽);
        assertThat(dao.getCount(), is(2));

        //then
        User findFrankly = dao.get("frankly");
        assertThat(findFrankly.getName(), is(재엽정.getName()));
        assertThat(findFrankly.getPassword(), is(재엽정.getPassword()));
        assertThat(findFrankly.getId(), is(재엽정.getId()));

        User findFrankle = dao.get("frankle");
        assertThat(findFrankle.getName(), is(정재엽.getName()));
        assertThat(findFrankle.getPassword(), is(정재엽.getPassword()));
        assertThat(findFrankle.getId(), is(정재엽.getId()));
    }

    @Test
    void getCountTest() throws SQLException, ClassNotFoundException {
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(재엽정);
        assertThat(dao.getCount(), is(1));

        dao.add(정재엽);
        assertThat(dao.getCount(), is(2));

        dao.add(박우람);
        assertThat(dao.getCount(), is(3));
    }

    @Test
    void getUserFailure() throws SQLException {
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> dao.get("unknown_id"));
    }
}
