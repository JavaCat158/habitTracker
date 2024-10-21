package ru.yalab.out.persistence;

import ru.yalab.entity.User;
import ru.yalab.entity.UserRole;
import ru.yalab.entity.UserStatus;
import ru.yalab.out.database.DataBaseTemplate;
import ru.yalab.repository.UserRepository;

import java.sql.*;
import java.util.LinkedHashSet;

/*
public User(long id, String name, String email, String password, UserRole role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = UserStatus.ACTIVE; // По умолчанию пользователь активен
    }
*/

public class JDBCUserRepository implements UserRepository {

    private final DataBaseTemplate dataBaseTemplate;

    public JDBCUserRepository(DataBaseTemplate dataBaseTemplate) {
        this.dataBaseTemplate = dataBaseTemplate;
    }

    @Override
    public void addUser(User user) throws SQLException {
        String sql = "INSERT UNTI table_schema.users (name, email, password) VALUES (?, ?, ?))";

        try (Connection conn = dataBaseTemplate.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public User findByEmail(String email) throws SQLException {
        User user = null;
        String sql = "SELECT * FROM table_schema.users WHERE email = ?";
        try (Connection conn = dataBaseTemplate.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "email");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    long id = rs.getLong("id");
                    String name = rs.getString("name");
                    String password = rs.getString("password");
                    String roleStr = rs.getString("role");

                    UserRole role = UserRole.valueOf(roleStr.toUpperCase());

                    user = new User(id, name, email, password, role);
                }
            }
        }
        return user;
    }

    @Override
    public void deleteByEmail(String email) throws SQLException {
        String sql = "DELETE FROM table_schema.users WHERE email= ?";
        try (Connection conn = dataBaseTemplate.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.executeUpdate();
        }
    }

    @Override
    public LinkedHashSet<User> findAll() throws SQLException {
        LinkedHashSet<User> users = new LinkedHashSet<>();
        String sql = "SELECT * FROM table_schema.users";
        try (Connection conn = dataBaseTemplate.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String role = rs.getString("role");
                User user = new User(id, name, email, password, UserRole.valueOf(role));
                users.add(user);
            }
        }
        return users;
    }
}
