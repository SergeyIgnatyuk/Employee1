package com.dao.jpa;

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
import java.util.Properties;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaRoleDaoImplTest.JpaRoleDaoImplTestConfig.class})
@Transactional
public class JpaRoleDaoImplTest {
    @Autowired
    private RoleDao roleDao;

    @Configuration
    @EnableTransactionManagement
    @ComponentScan(basePackages = "com.dao.jpa")
    static class JpaRoleDaoImplTestConfig {
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
    public void getOneTest() {
        Role role = roleDao.getOne(1L);
        Assert.assertEquals(1, role.getId().intValue());
        Assert.assertEquals("ROLE_ADMIN", role.getName());
    }
}