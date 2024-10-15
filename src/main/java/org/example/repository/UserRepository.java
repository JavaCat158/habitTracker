package org.example.repository;

import org.example.entity.User;

import java.util.List;

/**
 * Интерфейс UserRepository определяет контракт для работы с объектами пользователей (User).
 * Он включает методы для управления пользователями, такими как сохранение, поиск и удаление.
 *
 * Методы включают:
 * - Сохранение пользователя.
 * - Поиск пользователя по адресу электронной почты.
 * - Удаление пользователя по адресу электронной почты.
 * - Получение списка всех пользователей.
 */


public interface UserRepository {
    void save(User user);

    User findByEmail(String email);

    void deleteByEmail(String email);

    List<User> findAll();
}
