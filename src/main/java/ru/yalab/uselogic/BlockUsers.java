package ru.yalab.uselogic;

import ru.yalab.entity.User;
import ru.yalab.entity.UserStatus;
import ru.yalab.repository.UserRepository;

import java.sql.SQLException;

/**
 * Класс BlockUsers предоставляет логику для блокировки пользователей.
 * <p>
 * Поля:
 * - userRepository: репозиторий пользователей, используемый для доступа к данным пользователей.
 * <p>
 * Методы:
 * - execute(String email): блокирует пользователя с указанным адресом электронной почты.
 * Возвращает true, если пользователь был успешно найден и заблокирован; иначе возвращает false.
 */

public class BlockUsers {
    private final UserRepository userRepository;

    public BlockUsers(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean execute(String email) throws SQLException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.blockUser();
            return true;
        }

        return false;
    }

    public boolean unBlock(String email) throws SQLException {
        User user = userRepository.findByEmail(email);
        if (user.getStatus() == UserStatus.BLOCKED) {
            user.unblockUser();
            return true;
        }
        return false;
    }
}
