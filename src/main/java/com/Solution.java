package com;

import com.dao.UserDao;
import com.dao.jdbc.JdbcUserDaoImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Solution {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring/conf-root.xml");

        UserDao userDao = (JdbcUserDaoImpl) context.getBean("jdbcUserDaoImpl");

        System.out.println(userDao.findByUsername("admin"));
    }
}
