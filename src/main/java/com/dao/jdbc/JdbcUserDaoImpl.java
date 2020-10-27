package com.dao.jdbc;

import com.dao.UserDao;
import com.model.Role;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class JdbcUserDaoImpl implements UserDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcUserDaoImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private ResultSetExtractor<User> resultSetExtractor() {
        return new ResultSetExtractor<User>() {
            @Override
            public User extractData(ResultSet rs) throws SQLException, DataAccessException {
                User user = null;
                while (rs.next()) {
                    user = new User();
                    user.setId(rs.getLong("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setRoles(new HashSet<>());

                    Role role = new Role();
                    role.setId(rs.getLong("role_id"));
                    role.setName(rs.getString("name"));
                    user.getRoles().add(role);
                }
                return user;
            }
        };
    }

    private RowMapper<User> rowMapper() {
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int i) throws SQLException {
                User user = new User();
                user.setId(rs.getLong("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                Set<Role> roles = new HashSet<>();
                Role role = new Role();
                role.setId(rs.getLong("role_id"));
                role.setName(rs.getString("name"));
                roles.add(role);
                user.setRoles(roles);
                return user;
            }
        };
    }

    @Override
    public User save(User user) {
        String sql1 = "INSERT INTO users (username, password) VALUES (:username, :password)";
        String sql2 = "INSERT INTO user_roles VALUES ((SELECT user_id FROM users WHERE username = :username), " +
                "(SELECT role_id FROM roles WHERE name = :role))";

        Map<String, Object> params = new HashMap<>();
        params.put("username", user.getUsername());
        params.put("password", user.getPassword());
        namedParameterJdbcTemplate.update(sql1, params);
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            params.put("role", role.getName());
            namedParameterJdbcTemplate.update(sql2, params);
        }
        return user;
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT u.user_id, u.username, u.password, r.role_id, r.name " +
                "FROM users u " +
                "LEFT JOIN user_roles ur ON u.user_id = ur.user_id " +
                "LEFT JOIN roles r ON r.role_id = ur.role_id " +
                "WHERE u.username = :username";

        Map<String, Object> params = new HashMap<>();
        params.put("username", username);

        return namedParameterJdbcTemplate.query(sql, params, resultSetExtractor());
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT u.user_id, u.username, u.password, r.role_id, r.name " +
                "FROM users u " +
                "LEFT JOIN user_roles ur ON ur.user_id = u.user_id " +
                "LEFT JOIN roles r ON r.role_id = ur.role_id";

        return namedParameterJdbcTemplate.query(sql, rowMapper());
    }
}
