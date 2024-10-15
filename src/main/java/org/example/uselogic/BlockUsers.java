package org.example.uselogic;

import org.example.entity.User;
import org.example.repository.UserRepository;

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

    public boolean execute(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.blockUser();
            return true;
        }

        return false;
    }

    public boolean unBlock(String email) {
        User user = userRepository.findByEmail(email);
        if (user.isBloced()) {
            user.unblockUser();
            return true;
        }
        return false;
    }
}
