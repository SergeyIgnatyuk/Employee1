package com.dao.hibernate;

import com.dao.UserDao;
import com.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of {@link UserDao} interface.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@Repository
public class HibernateUserDaoImpl implements UserDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public HibernateUserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<User> findAll() {
        Query query = currentSession().createQuery("From User");
        return query.getResultList();
    }

    @Override
    public User findByUsername(String username) {
        Query query = currentSession().createQuery("FROM User WHERE username = :username");
        query.setParameter("username", username);
        List<User> users = query.list();
        if (users.size() > 0) {
            return (User) users.get(0);
        }
        return null;
    }

    @Override
    public User save(User user) {
        currentSession().save(user);
        return user;
    }
}
