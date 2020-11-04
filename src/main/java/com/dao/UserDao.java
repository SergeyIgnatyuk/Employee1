package com.dao;

import com.model.User;

import java.util.List;

/**
 * DAO interface for {@link com.model.User}
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

public interface UserDao {
    List<User> findAll();
    User findByUsername(String username);
    User save(User user);
}

