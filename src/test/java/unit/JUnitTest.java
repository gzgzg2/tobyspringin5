package unit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.user.dao.UserDao;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class JUnitTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    UserDao userDao;

    static Set<JUnitTest> test = new HashSet<>();

    static ApplicationContext testContext = null;

    @Test
    public void test1() {
        assertThat(test, not(hasItem(this)));
        test.add(this);

        assertThat(testContext == null || testContext == applicationContext,
                is(true));

        testContext = this.applicationContext;
    }

    @Test
    public void test2() {
        assertThat(test, not(hasItem(this)));
        test.add(this);

        assertTrue(testContext == null || testContext == applicationContext);
        testContext = this.applicationContext;
    }

    @Test
    public void test3() {
        assertThat(test, not(hasItem(this)));
        test.add(this);

        assertThat(testContext,
                either(is(nullValue())).or(is(this.applicationContext)));
        testContext = this.applicationContext;
    }

    @Test
    public void sameBeanTest() {
        assertSame(userDao, applicationContext.getBean("userDao"));
    }
}
