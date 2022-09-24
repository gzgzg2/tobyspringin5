package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
public class DaoFactory {

    @Bean
    public UserDao userDao() {
        UserDao userDao = new UserDao();
        return userDao;
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setUrl("jdbc:mysql://localhost/study");
        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUsername("uram");
        dataSource.setPassword("qkr!dnfka12");
        return dataSource;
    }

    @Bean
    public MessageDao messageDao() {
        ConnectionMaker connectionMaker = connectionMaker();
        return new MessageDao(connectionMaker);
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new CountingConnectionMaker(simpleConnectionMaker());
    }

    @Bean
    public ConnectionMaker simpleConnectionMaker() {
        return new SimpleConnectionMaker();
    }
}
