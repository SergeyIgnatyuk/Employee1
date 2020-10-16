package com.dao;

import com.model.User;

import java.util.List;

public interface UserDao {
    User save(User user);
    User findByUsername(String username);
    List<User> findAll();
}

