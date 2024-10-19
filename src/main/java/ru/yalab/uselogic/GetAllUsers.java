package ru.yalab.uselogic;

import ru.yalab.entity.User;
import ru.yalab.repository.UserRepository;

import java.sql.SQLException;
import java.util.LinkedHashSet;

/**
 * Класс GetAllUsers предоставляет логику для получения списка всех пользователей.
 * <p>
 * Поля:
 * - userRepository: репозиторий пользователей, используемый для доступа к данным о пользователях.
 * <p>
 * Методы:
 * - execute():
 * Возвращает список всех пользователей из репозитория.
 *
 * @return список пользователей.
 */

public class GetAllUsers {
    private final UserRepository userRepository;

    public GetAllUsers(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LinkedHashSet<User> execute() throws SQLException {
        return userRepository.findAll();
    }
}
