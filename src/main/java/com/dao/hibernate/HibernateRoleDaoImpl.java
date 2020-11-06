package com.dao.hibernate;

import com.dao.RoleDao;
import com.model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
