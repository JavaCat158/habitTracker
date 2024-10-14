package org.example.out.persistence;

import org.example.entity.User;
import org.example.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс InMemoryUserRepository реализует интерфейс UserRepository и предоставляет методы
 * для управления пользователями, которые хранятся в памяти.
 *
 * Методы включают:
 * - Сохранение нового пользователя.
 * - Поиск пользователя по его email.
 * - Удаление пользователя по его email.
 * - Получение списка всех пользователей.
 */


public class InMemoryUserRepository implements UserRepository {

    private final List<User> users = new ArrayList<>();

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public User findByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteByEmail(String email) {
        users.removeIf(user -> user.getEmail().equals(email));
    }

    @Override
    public List<User> findAll() {
        return users;
    }
}
