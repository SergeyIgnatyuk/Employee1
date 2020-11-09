package com.dao.jpa;

import com.dao.RoleDao;
import com.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Implementation of {@link RoleDao} interface.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@Repository
public class JpaRoleDaoImpl implements RoleDao{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role getOne(Long id) {
        return entityManager.find(Role.class, id);
    }
}
