package com.service;

import com.dao.RoleDao;
import com.dao.UserDao;
import com.model.Role;
import com.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UserServiceImplTest.UserServiceImplTestConfig.class)
public class UserServiceImplTest {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserService userService;

    @Configuration
    static class UserServiceImplTestConfig {

        @Bean()
        public UserDao userDao() {
            return Mockito.mock(UserDao.class);
        }

        @Bean()
        public RoleDao roleDao() {
            return Mockito.mock(RoleDao.class);
        }

        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {
            return new BCryptPasswordEncoder(11);
        }

        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @Test
    public void saveTest() {
        User testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("123");
        String password = testUser.getPassword();

        Role role = new Role();
        role.setId(2L);
        role.setName("USER_ROLE");

        when(roleDao.getOne(2L)).thenReturn(role);

        userService.save(testUser);

        Assert.assertNotEquals(testUser.getPassword(), password);

        verify(roleDao, times(1)).getOne(2L );
        Set<Role> roles = testUser.getRoles();
        for (Role r : roles) {
            Assert.assertEquals(r.getName(), role.getName());
        }
        Assert.assertEquals(roles.size(), 1);

        verify(userDao, times(1)).save(testUser);
    }

    @Test
    public void findByUsernameTest() {
        userService.findByUsername("admin");
        verify(userDao, times(1)).findByUsername("admin");
    }

    @Test
    public void findAllTest() {
        userService.findAll();
        verify(userDao, times(1)).findAll();
    }
}
