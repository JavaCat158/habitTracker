package org.example.uselogic;

import org.example.entity.User;
import org.example.repository.UserRepository;

import java.util.List;

/**
 * Класс GetAllUsers предоставляет логику для получения списка всех пользователей.
 *
 * Поля:
 * - userRepository: репозиторий пользователей, используемый для доступа к данным о пользователях.
 *
 * Методы:
 * - execute():
 * Возвращает список всех пользователей из репозитория.
 *
 * @return список пользователей.
 */

public class GetAllUsers {
    private UserRepository userRepository;

    public GetAllUsers(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> execute() {
        return userRepository.findAll();
    }
}
