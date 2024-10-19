package ru.yalab.repository;

import ru.yalab.entity.User;

import java.sql.SQLException;
import java.util.LinkedHashSet;

/**
 * Интерфейс UserRepository определяет контракт для работы с объектами пользователей (User).
 * Он включает методы для управления пользователями, такими как сохранение, поиск и удаление.
 * <p>
 * Методы включают:
 * - Сохранение пользователя.
 * - Поиск пользователя по адресу электронной почты.
 * - Удаление пользователя по адресу электронной почты.
 * - Получение списка всех пользователей.
 */


public interface UserRepository {
    void save(User user) throws SQLException;

    User findByEmail(String email) throws SQLException;

    void deleteByEmail(String email) throws SQLException;

    LinkedHashSet<User> findAll() throws SQLException;
}
