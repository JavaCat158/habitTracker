package ru.yalab.out.persistence;

import ru.yalab.entity.User;
import ru.yalab.entity.UserRole;
import ru.yalab.entity.UserStatus;
import ru.yalab.out.database.DataBaseTemplate;
import ru.yalab.repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Класс InMemoryUserRepository реализует интерфейс UserRepository и предоставляет методы
 * для управления пользователями, которые хранятся в памяти.
 * <p>
 * Методы включают:
 * - Сохранение нового пользователя.
 * - Поиск пользователя по его email.
 * - Удаление пользователя по его email.
 * - Получение списка всех пользователей.
 */


public class InMemoryUserRepository implements UserRepository {
    DataBaseTemplate dataBaseTemplate = new DataBaseTemplate();
    private final LinkedHashSet<User> users = new LinkedHashSet<>();


    public InMemoryUserRepository() {
        this.dataBaseTemplate = dataBaseTemplate;
    }

    @Override
    public void save(User user) throws SQLException {
        String sql = "INSERT INTO table_schema.users (name, email, password, role, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dataBaseTemplate.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword()); // Здесь можно добавить хеширование пароля
            statement.setString(4, user.getRole().name());
            statement.setString(5, user.getStatus().name());
            statement.executeUpdate();
        }
    }

    @Override
    public User findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM table_schema.users WHERE email = ?";
        try (Connection connection = dataBaseTemplate.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        UserRole.valueOf(resultSet.getString("role"))
                );
            }
            return null;
        }
    }

    @Override
    public void deleteByEmail(String email) throws SQLException {
        String sql = "DELETE FROM table_schema.users WHERE email = ?";
        try (Connection connection = dataBaseTemplate.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.executeUpdate();
        }
    }

    @Override
    public LinkedHashSet<User> findAll() throws SQLException {
        LinkedHashSet<User> users = new LinkedHashSet<>();
        String sql = "SELECT * FROM table_schema.users";
        try (Connection connection = dataBaseTemplate.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        UserRole.valueOf(resultSet.getString("role"))
                );
                users.add(user);
            }
        }
        return users;
    }
}