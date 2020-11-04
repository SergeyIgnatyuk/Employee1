package com.dao.jdbc;

import com.dao.EmployeeDao;
import com.dao.RoleDao;
import com.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@link RoleDao} interface.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@Repository
public class JdbcRoleDaoImpl implements RoleDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcRoleDaoImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private RowMapper<Role> rowMapper() {
        return (rs, i) -> {
            Role role = new Role();
            role.setId(rs.getLong("role_id"));
            role.setName(rs.getString("name"));
            return role;
        };
    }

    @Override
    public Role getOne(Long id) {
        String sql = "SELECT role_id, name FROM roles WHERE role_id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, params, rowMapper());
    }
}
