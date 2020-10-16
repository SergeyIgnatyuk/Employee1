package com.dao.jdbc;

import com.dao.RoleDao;
import com.model.Role;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JdbcRoleDaoImplTest.JdbcRoleDaoImplTestConfig.class)
@Transactional
public class JdbcRoleDaoImplTest {
    @Autowired
    private RoleDao roleDao;

    @Configuration
    @EnableTransactionManagement
    @ComponentScan(basePackages = "com.dao.jdbc")
    static class JdbcRoleDaoImplTestConfig {
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
    @Rollback
    public void getOneTest() {
        Role role = roleDao.getOne(1L);

        Assert.assertEquals(1, role.getId().intValue());
        Assert.assertEquals("ROLE_ADMIN", role.getName());
    }
}
