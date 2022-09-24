package springbook.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

import java.sql.SQLException;


public class Main {


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ApplicationContext applicationContext = new GenericXmlApplicationContext("applicationContext.xml");
        UserDao userDao = applicationContext.getBean("userDao", UserDao.class);

        User user = new User();

        user.setId("frankle");
        user.setName("정재엽");
        user.setPassword("바보");

        userDao.add(user);


        User findUser = userDao.get("frankle");

        if (!findUser.getName().equals(user.getName())) {
            System.out.println("테스트 실패 (name)");
        }  else if (!findUser.getPassword().equals(user.getPassword())) {
            System.out.println("테스트 실패 (password)");
        } else {
            System.out.println("조회 테스트 성공");
        }
    }
}
