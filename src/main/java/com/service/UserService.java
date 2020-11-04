package com.service;

import com.model.User;

import java.util.List;

/**
 * Service interface for {@link com.model.User}
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

public interface UserService {
    List<User> findAll();
    User findByUsername(String username);
    void save(User user);
}
