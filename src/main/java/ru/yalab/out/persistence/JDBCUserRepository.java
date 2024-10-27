package ru.yalab.out.persistence;

import ru.yalab.entity.User;
import ru.yalab.entity.UserRole;
import ru.yalab.entity.UserStatus;
import ru.yalab.out.database.DataBaseTemplate;
import ru.yalab.repository.UserRepository;

import java.sql.*;
import java.util.LinkedHashSet;

public class JDBCUserRepository implements UserRepository {

    private final DataBaseTemplate dataBaseTemplate;

    public JDBCUserRepository(DataBaseTemplate dataBaseTemplate) {
        this.dataBaseTemplate = dataBaseTemplate;
    }

    @Override
    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO table_schema.users (name, email, password) VALUES (?, ?, ?)";

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
        String sql = "select * from table_schema.users where email=?";
        try (Connection conn = dataBaseTemplate.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    long id = rs.getLong("id");
                    String name = rs.getString("name");
                    String password = rs.getString("password");
                    String role = rs.getString("role");
                    String status = rs.getString("status");

                    UserRole roleString = UserRole.valueOf(role);
                    UserStatus statusString = UserStatus.valueOf(status);

                    user = new User(id, name, email, password, roleString, statusString);
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
        } catch (SQLException e) {
            e.printStackTrace();
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
                String status = rs.getString("status");

                UserStatus statusString = UserStatus.valueOf(status);
                UserRole roleString = UserRole.valueOf(role);

                User user = new User(id, name, email, password, roleString, statusString);
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public void updateUserProfile(User user) throws SQLException {
        String sql = "UPDATE table_schema.users SET name = ?, " +
                "password = ?, " +
                "email = ?, " +
                "role = ?, " +
                "status = ? ";
        try (Connection conn = dataBaseTemplate.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getRole().name());
            stmt.setString(5, user.getStatus().name());
            stmt.executeUpdate();
        }
    }

    @Override
    public void blockUser(String email) throws SQLException {
        String sql = "UPDATE table_schema.users SET status = ? WHERE email = ?";
        try (Connection conn = dataBaseTemplate.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, UserStatus.BLOCKED.name());
            stmt.setString(2, email);
            stmt.executeUpdate();
        }
    }
}
