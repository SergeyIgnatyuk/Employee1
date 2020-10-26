package com.service;

import com.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findByUsername(String username);
    void save(User user);
    User editUserByUsername(String username, String newUsername, String roleName, String password);
    void deleteUserByUsername(String username);
}
