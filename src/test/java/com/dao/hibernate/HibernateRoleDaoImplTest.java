package com.dao.hibernate;

import com.dao.EmployeeDao;
import com.dao.RoleDao;
import com.model.Role;
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
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Properties;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateRoleDaoImplTest.HibernateRoleDaoImplTestConfig.class})
@Transactional
public class HibernateRoleDaoImplTest {
    @Autowired
    private RoleDao roleDao;

    @Configuration
    @EnableTransactionManagement
    @ComponentScan(basePackages = "com.dao.hibernate")
    static class HibernateRoleDaoImplTestConfig {
        @Bean
        public DataSource dataSource() {
            EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
            return builder.setType(EmbeddedDatabaseType.H2).addScript("classpath:h2.sql").build();
        }

        @Bean
        public LocalSessionFactoryBean sessionFactory() {
            Properties hibernateProperties = new Properties();
            hibernateProperties.setProperty("dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
            hibernateProperties.setProperty("show_sql", "true");
            hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");

            LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
            sessionFactory.setDataSource(dataSource());
            sessionFactory.setMappingResources("hibernate/role.hbm.xml", "hibernate/user.hbm.xml");
            sessionFactory.setHibernateProperties(hibernateProperties);
            return sessionFactory;
        }

        @Bean
        public TransactionManager transactionManager() {
            HibernateTransactionManager transactionManager = new HibernateTransactionManager();
            transactionManager.setSessionFactory(sessionFactory().getObject());
            return transactionManager;
        }

        @Bean
        public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
            return new PersistenceExceptionTranslationPostProcessor();
        }
    }

    @Test
    @Rollback(value = true)
    public void getOneTest() {
        Role role = roleDao.getOne(1L);
        Assert.assertEquals(1, role.getId().intValue());
        Assert.assertEquals("ROLE_ADMIN", role.getName());
    }
}