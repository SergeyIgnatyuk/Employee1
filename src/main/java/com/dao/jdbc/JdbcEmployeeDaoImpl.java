package com.dao.jdbc;

import com.dao.EmployeeDao;
import com.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcEmployeeDaoImpl implements EmployeeDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcEmployeeDaoImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private RowMapper<Employee> rowMapper() {
        return (rs, i) -> {
            Employee employee = new Employee();
            employee.setId(rs.getLong("id"));
            employee.setFirstName(rs.getString("first_name"));
            employee.setLastName(rs.getString("last_name"));
            employee.setDepartmentId(rs.getString("department_id"));
            employee.setJobTitle(rs.getString("job_title"));
            employee.setGender(rs.getString("gender"));
            employee.setDateOfBirth(rs.getDate("date_of_birth"));
            return employee;
        };
    }

    @Override
    public List<Employee> findAllEmployees() {
        String sql = "SELECT id, first_name, last_name, department_id, job_title, gender, date_of_birth " +
                "FROM employees";
        return namedParameterJdbcTemplate.query(sql, rowMapper());
    }

    @Override
    public Employee findEmployeeById(Long id) {
        String sql = "SELECT id, first_name, last_name, department_id, job_title, gender, date_of_birth " +
                "FROM employees " +
                "WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, params, rowMapper());
    }

    @Override
    public void addNewEmployee(Employee employee) {
        String sql = "INSERT INTO employees (first_name, last_name, department_id, job_title, gender, date_of_birth) " +
                "VALUES (:firstName, :lastName, :departmentId, :jobTitle, :gender, :dateOfBirth)";
        Map<String, Object> params = new HashMap<>();
        params.put("firstName", employee.getFirstName());
        params.put("lastName", employee.getLastName());
        params.put("departmentId", employee.getDepartmentId());
        params.put("jobTitle", employee.getJobTitle());
        params.put("gender", employee.getGender());
        params.put("dateOfBirth", employee.getDateOfBirth());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public Employee updateEmployeeById(Long id, String departmentId, String jobTitle) {
        String sql = "UPDATE employees " +
                "SET department_id = :departmentId, job_title = :jobTitle " +
                "WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("departmentId", departmentId);
        params.put("jobTitle", jobTitle);
        namedParameterJdbcTemplate.update(sql, params);
        return findEmployeeById(id);
    }

    @Override
    public void deleteEmployeeById(Long id) {
        String sql = "DELETE FROM employees WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        namedParameterJdbcTemplate.update(sql, params);
    }
}
