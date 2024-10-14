package org.example.uselogic;

import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.services.EmailValidator;

/**
 * Класс RegisterUser реализует логику регистрации нового пользователя.
 *
 * Поля:
 * - userRepository: репозиторий пользователей, используемый для доступа к данным о пользователях.
 *
 * Методы:
 * - execute(String username, String email, String password, boolean isAdmin):
 * Регистрирует нового пользователя с заданным именем пользователя, адресом электронной почты, паролем и правами администратора.
 *
 * @param username имя пользователя.
 * @param email адрес электронной почты пользователя.
 * @param password пароль пользователя.
 * @param isAdmin флаг, указывающий, является ли пользователь администратором.
 * @return true, если пользователь успешно зарегистрирован; false, если пользователь с таким email уже существует
 * или некорректный вид email.
 */

public class RegisterUser {
    private final UserRepository userRepository;

    public RegisterUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean execute(String username, String email, String password, boolean isAdmin) {
        if (!EmailValidator.isValidate(email)) {
            System.out.println("Некорректный email. Email должен быть вида буквы123@test.ru");
            return false;
        }

        if (userRepository.findByEmail(email) != null) {
            return false; // пользователь с таким email уже существует
        }

        User user = new User(username, email, password, isAdmin);
        userRepository.save(user);
        return true;
    }
}
