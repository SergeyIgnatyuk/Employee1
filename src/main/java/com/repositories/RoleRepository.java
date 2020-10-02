package com.repositories;

import com.dao.RoleDao;
import com.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends RoleDao, JpaRepository<Role, Long> {
}
