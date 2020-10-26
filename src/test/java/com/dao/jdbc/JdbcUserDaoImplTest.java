package com.dao.jdbc;

import com.dao.UserDao;
import com.model.Role;
import com.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JdbcUserDaoImplTest.JdbcUserDaoImplTestConfig.class})
@Transactional
public class JdbcUserDaoImplTest {

    @Autowired
    private UserDao userDao;

    @Configuration
    @EnableTransactionManagement
    @ComponentScan(basePackages = "com.dao.jdbc")
    static class JdbcUserDaoImplTestConfig {
        @Bean
        public DataSource dataSource() {
            EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
            return builder.setType(EmbeddedDatabaseType.H2).addScript("classpath:h2.sql").build();
        }

        @Bean
        public TransactionManager transactionManager() {
            return new DataSourceTransactionManager(dataSource());
        }
    }

    @Test
    @Rollback(value = true)
    public void saveTest() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("123");
        Role role = new Role();
        role.setName("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        userDao.save(user);
        User testUser = userDao.findByUsername(user.getUsername());

        Assert.assertEquals(2, testUser.getId().intValue());
        Assert.assertEquals(testUser.getUsername(), user.getUsername());
        Assert.assertEquals(testUser.getPassword(), user.getPassword());

        Set<Role> testRoles = testUser.getRoles();
        for (Role testRole : testRoles) {
            Assert.assertEquals(role.getName(), testRole.getName());
        }
    }

    @Test
    @Rollback(value = true)
    public void findByUsernameTest() {
        String username = "admin";
        User user = userDao.findByUsername(username);

        Assert.assertEquals(1, user.getId().intValue());
        Assert.assertEquals("admin", user.getUsername());

        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            Assert.assertEquals("ROLE_ADMIN", role.getName());
        }
    }

    @Test
    @Rollback(value = true)
    public void findAllTest() {
        List<User> userList = userDao.findAll();

        Assert.assertEquals(1, userList.size());
        Assert.assertEquals(1, userList.get(0).getId().intValue());
        Assert.assertEquals("admin", userList.get(0).getUsername());
        Set<Role> roles = userList.get(0).getRoles();
        for (Role role : roles) {
            Assert.assertEquals("ROLE_ADMIN", role.getName());
        }
    }

    @Test
    public void deleteUserByUsernameTest() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("123");
        Role role = new Role();
        role.setName("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        userDao.save(user);

        List<User> users = userDao.findAll();
        Assert.assertEquals(2, users.size());
        userDao.deleteUserByUsername(user.getUsername());
        users = userDao.findAll();
        Assert.assertEquals(1, users.size());
    }
}