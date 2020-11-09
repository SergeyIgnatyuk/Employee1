package com.dao.jpa;

import com.dao.RoleDao;
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
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaUserDaoImplTest.JpaUserDaoImplTestConfig.class})
@Transactional
public class JpaUserDaoImplTest {
    @Autowired
    private UserDao userDao;

    @Configuration
    @EnableTransactionManagement
    @ComponentScan(basePackages = "com.dao.jpa")
    static class JpaUserDaoImplTestConfig {
        @Bean
        public DataSource dataSource() {
            EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
            return builder.setType(EmbeddedDatabaseType.H2).addScript("classpath:h2.sql").build();
        }

        @Bean
        public LocalContainerEntityManagerFactoryBean emf() {
            LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
            emf.setDataSource(dataSource());
            emf.setPackagesToScan("com.model");

            JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
            emf.setJpaVendorAdapter(jpaVendorAdapter);

            Properties jpaProperties = new Properties();
            jpaProperties.setProperty("databasePlatform", "org.hibernate.dialect.H2Dialect");
            jpaProperties.setProperty("database", "H2");
            jpaProperties.setProperty("showSql", "true");
            jpaProperties.setProperty("generateDdl", "true");
            emf.setJpaProperties(jpaProperties);

            return emf;
        }

        @Bean
        public TransactionManager transactionManage() {
            return new JpaTransactionManager(emf().getObject());
        }

        @Bean
        public PersistenceAnnotationBeanPostProcessor persistenceAnnotationBeanPostProcessor() {
            return new PersistenceAnnotationBeanPostProcessor();
        }

        @Bean
        public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
            return new PersistenceExceptionTranslationPostProcessor();
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
}