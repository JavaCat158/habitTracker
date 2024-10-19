package ru.yalab.uselogic;

import ru.yalab.entity.User;
import ru.yalab.entity.UserRole;
import ru.yalab.repository.UserRepository;
import ru.yalab.services.EmailValidator;

import java.sql.SQLException;

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

    public boolean execute(String username, String email, String password, UserRole userRole) throws SQLException {
        if (!EmailValidator.isValidate(email)) {
            System.out.println("Некорректный email. Email должен быть вида буквы123@test.ru");
            return false;
        }

        if (userRepository.findByEmail(email) != null) {
            System.out.println("Пользователь с таким email уже существует!");
            return false; // пользователь с таким email уже существует
        }

        User user = new User(username, email, password, userRole);
        userRepository.save(user);
        return true;
    }
}
