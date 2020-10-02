package com.dao.jdbc;

import com.dao.UserDao;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcUserDaoImpl implements UserDao {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcUserDaoImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private RowMapper<User> rowMapper() {
        return (rs, i) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            return user;
        };
    }

    @Override
    public User save(User user) {
        String sql = "INSERT INTO users (username, password) VALUES (:username, :password)";
        Map<String, Object> params = new HashMap<>();
        params.put("username", user.getUsername());
        params.put("password", user.getPassword());
        namedParameterJdbcTemplate.update(sql, params);
        return user;
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT id, username, password FROM users WHERE username := username";
        Map<String, Object> params = new HashMap<>();
        params.put("params", username);
        return namedParameterJdbcTemplate.queryForObject(sql, params, rowMapper());
    }
}
