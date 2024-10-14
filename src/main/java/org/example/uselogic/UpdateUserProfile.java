package org.example.uselogic;

import org.example.entity.Habit;
import org.example.entity.User;
import org.example.repository.HabitRepository;
import org.example.repository.UserRepository;
import org.example.services.EmailValidator;

import java.util.List;

/**
 * Класс UpdateUserProfile отвечает за обновление профиля пользователя.
 *
 * Поля:
 * - userRepository: репозиторий пользователей, используемый для доступа к данным о пользователях.
 *
 * Методы:
 * - execute(String currentEmail, String newName, String newEmail, String newPassword):
 * Обновляет информацию о пользователе, включая имя, email и пароль.
 *
 * @param currentEmail текущий адрес электронной почты пользователя, чьи данные необходимо обновить.
 * @param newName новое имя пользователя.
 * @param newEmail новый адрес электронной почты пользователя.
 * @param newPassword новый пароль пользователя.
 *
 * @return true, если обновление было успешным; false, если пользователь не найден,
 *         email уже используется или одна из строк пуста или некорректный вид email.
 */

public class UpdateUserProfile {
    private UserRepository userRepository;
    private HabitRepository habitRepository;

    public UpdateUserProfile(UserRepository userRepository, HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
        this.userRepository = userRepository;
    }

    public boolean execute(String currentEmail, String newName, String newEmail, String newPassword) {
        User user = userRepository.findByEmail(currentEmail);

        if(!EmailValidator.isValidate(newEmail)) {
            System.out.println("Некорректный email. Email должен быть вида буквы123@test.ru");
        }

        if (user == null) {
            System.out.println("Пользователь не найден.");
            return false;
        }

        if (!currentEmail.equals(newEmail) && userRepository.findByEmail(newEmail) != null) {
            System.out.println("Email уже используется!");
            return false;
        }

        if (newPassword.isEmpty() || newEmail.isEmpty() || newName.isEmpty()) {
            System.out.println("Одна из строк пуста.");
            return false;
        }

        user.setName(newName);
        user.setEmail(newEmail);
        user.setPassword(newPassword);

        userRepository.deleteByEmail(currentEmail);

        // Обновление email в привычках пользователя
        List<Habit> habits = habitRepository.finaAllByUserEmail(currentEmail);
        for (Habit habit : habits) {
            habit.setUserEmail(newEmail);  // Обновление email в привычке
        }

        return true;
    }
}
