package ru.yalab.uselogic;

import ru.yalab.entity.User;
import ru.yalab.repository.UserRepository;

import java.sql.SQLException;

/**
 * Класс DeleteUserProfile предоставляет логику для удаления профилей пользователей.
 *
 * Поля:
 * - userRepository: репозиторий пользователей, используемый для доступа к данным пользователей.
 *
 * Методы:
 * - deleteUser(String email):
 * Находит пользователя по указанному адресу электронной почты.
 * Если пользователь найден, он удаляется из репозитория, и метод возвращает true.
 * В противном случае возвращается false.
 */

public class DeleteUserProfile {
    private UserRepository userRepository;

    public DeleteUserProfile(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean deleteUser(String email) throws SQLException {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            userRepository.deleteByEmail(email);
            return true;
        }
        return false;
    }
}
