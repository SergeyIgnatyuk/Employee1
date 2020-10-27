package com.dao;

import com.model.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();
    User findByUsername(String username);
    User save(User user);
}

