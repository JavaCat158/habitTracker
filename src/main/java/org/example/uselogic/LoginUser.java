package org.example.uselogic;

import org.example.entity.User;
import org.example.repository.UserRepository;

/**
 * Класс LoginUser реализует логику для входа пользователя в систему.
 *
 * Поля:
 * - userRepository: репозиторий пользователей, используемый для доступа к данным о пользователях.
 *
 * Методы:
 * - execute(String email, String password):
 * Проверяет, может ли пользователь войти в систему с указанными учетными данными.
 *
 * @param email адрес электронной почты пользователя.
 * @param password пароль пользователя.
 * @return true, если вход выполнен успешно; false в противном случае (пользователь не найден или заблокирован).
 *
 * - getUser(String email):
 * Возвращает объект User для указанного адреса электронной почты.
 *
 * @param email адрес электронной почты пользователя.
 * @return объект User, соответствующий указанной электронной почте; null, если пользователь не найден.
 */

public class LoginUser {
    private final UserRepository userRepository;

    public LoginUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean execute(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return false;
        }

        if(user.isBloced()) {
            System.out.println("Вы заблокированы обратитесь в поддержку!");;
            return false;
        }

        return user.getPassword().equals(password);
    }

    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }
}
