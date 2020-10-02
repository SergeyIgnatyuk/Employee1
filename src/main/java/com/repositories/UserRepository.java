package com.repositories;

import com.dao.UserDao;
import com.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends UserDao, JpaRepository<User, Long> {
    User findByUsername(String username);
}
