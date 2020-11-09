package com.dao.jpa;

import com.dao.UserDao;
import com.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Implementation of {@link UserDao} interface.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@Repository
public class JpaUserDaoImpl implements UserDao{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findAll() {
        Query query = entityManager.createQuery("FROM User");
        return query.getResultList();
    }

    @Override
    public User findByUsername(String username) {
        Query query = entityManager.createQuery("FROM User WHERE username = :username");
        query.setParameter("username", username);
        List<User> users = query.getResultList();
        if (users.size() > 0) {
            return users.get(0);
        }
        return null;
    }

    @Override
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }
}
