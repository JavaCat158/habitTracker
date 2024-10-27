package ru.yalab.uselogic;

import ru.yalab.entity.Habit;
import ru.yalab.entity.User;
import ru.yalab.repository.HabitRepository;
import ru.yalab.repository.UserRepository;
import ru.yalab.services.NotificationService;

import java.sql.SQLException;
import java.util.List;

/**
 * Класс SendHabitReminder отвечает за отправку напоминаний о привычках пользователям.
 *
 * Поля:
 * - userRepository: репозиторий пользователей для доступа к данным о пользователях.
 * - habitRepository: репозиторий привычек для доступа к данным о привычках.
 * - notificationService: сервис уведомлений, используемый для отправки сообщений пользователям.
 *
 * Методы:
 * - execute(String userEmail):
 * Отправляет напоминания о привычках пользователю с указанным адресом электронной почты.
 *
 * @param userEmail адрес электронной почты пользователя, которому нужно отправить напоминания.
 *
 * @return void
 *
 * Примечания:
 * - Если пользователь с указанным email не найден, выводится сообщение "Пользователь не найден".
 * - Если у пользователя нет привычек, выводится сообщение "У пользователя нет привычек".
 * - Для каждой привычки пользователя отправляется уведомление с ее названием.
 */

public class SendHabitReminder {
    private final UserRepository userRepository;
    private final HabitRepository habitRepository;
    private final NotificationService notificationService;

    public SendHabitReminder(UserRepository userRepository,
                             HabitRepository habitRepository,
                             NotificationService notificationService) {
        this.userRepository = userRepository;
        this.habitRepository = habitRepository;
        this.notificationService = notificationService;
    }

    public void execute(String userEmail) throws SQLException {
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            System.out.println("Пользователь не найден");
            return;
        }

        List<Habit> habits = habitRepository.findAllByUserEmail(userEmail);
        if(habits.isEmpty()) {
            System.out.println("У пользователя нет привычек");
            return;
        }

        for(Habit habit : habits) {
            String message = "Напоминание: " + habit.getTitle();
            notificationService.sendNotification(user.getEmail(), message);
        }
    }
}
