package com.dao;

import com.model.Role;
/**
 * DAO interface for {@link com.model.Role}
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

public interface RoleDao {
    Role getOne(Long id);
}
