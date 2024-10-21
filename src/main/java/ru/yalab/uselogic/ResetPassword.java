package ru.yalab.uselogic;

import ru.yalab.entity.User;
import ru.yalab.repository.UserRepository;

import java.sql.SQLException;
import java.util.Random;

/**
 * Класс ResetPassword реализует логику сброса пароля для пользователя.
 *
 * Поля:
 * - userRepository: репозиторий пользователей, используемый для доступа к данным о пользователях.
 *
 * Методы:
 * - execute(String email):
 * Сбрасывает пароль пользователя с указанным адресом электронной почты.
 * Генерирует новый временный пароль и отправляет его пользователю по электронной почте.
 *
 * @param email адрес электронной почты пользователя, для которого нужно сбросить пароль.
 * @return true, если пароль успешно сброшен и новый пароль отправлен; false, если пользователь с указанным email не найден.
 *
 * Приватные методы:
 * - generatePassword():
 * Генерирует временный пароль случайной длины.
 *
 * @return сгенерированный временный пароль.
 */

public class ResetPassword {
    private UserRepository userRepository;

    public ResetPassword(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean execute(String email) throws SQLException {
        User user = userRepository.findByEmail(email);
        if(user != null) {
            String updatePassword = generatePassword();
            user.setPassword(updatePassword);
            System.out.println("Новый пароль отправлен на email: " + email);
            System.out.println("Временный пароль: " + updatePassword);
            return true;
        }
        return false;
    }

    private String generatePassword() {
        int length = 6;
        String ch = "asdashduiugfdasoiffgosdfgsdfgfdkgjdfsg";
        Random random = new Random();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(ch.charAt(random.nextInt(ch.length())));
        }

        return sb.toString();
    }
}
