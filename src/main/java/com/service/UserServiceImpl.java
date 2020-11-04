package com.service;

import com.dao.RoleDao;
import com.dao.UserDao;
import com.model.Role;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link UserService} interface.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    @Transactional
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.getOne(2L));
        user.setRoles(roles);
        userDao.save(user);
    }
}
