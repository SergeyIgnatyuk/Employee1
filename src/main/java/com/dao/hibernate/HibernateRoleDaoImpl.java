package com.dao.hibernate;

import com.dao.RoleDao;
import com.model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Implementation of {@link RoleDao} interface.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@Repository
public class HibernateRoleDaoImpl implements RoleDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public HibernateRoleDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session currentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public Role getOne(Long id) {
        return currentSession().get(Role.class, id);
    }
}
