package com.dao;

import com.model.User;

public interface UserDao {
    User save(User user);
    User findByUsername(String username);
}
