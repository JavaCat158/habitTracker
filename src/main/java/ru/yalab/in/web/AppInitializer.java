package ru.yalab.in.web;

import ru.yalab.controllers.NotificationController;
import ru.yalab.out.persistence.InMemoryHabitRepository;
import ru.yalab.out.persistence.InMemoryUserRepository;
import ru.yalab.services.EmailNotificationService;
import ru.yalab.uselogic.SendHabitReminder;

/**
 * Класс AppInitializer отвечает за инициализацию и конфигурацию необходимых компонентов
 * для системы уведомлений приложения. Он настраивает репозитории пользователей и привычек,
 * сервис уведомлений по электронной почте и логику отправки напоминаний о привычках.
 *
 * Метод `init` возвращает полностью настроенный NotificationController, готовый
 * обрабатывать уведомления о привычках по электронной почте.
 */

public class AppInitializer {
    public NotificationController init() {
        InMemoryUserRepository userRepository = new InMemoryUserRepository();
        InMemoryHabitRepository habitRepository = new InMemoryHabitRepository();

        EmailNotificationService emailNotificationService = new EmailNotificationService();

        SendHabitReminder sendHabitReminder = new SendHabitReminder(
                userRepository,
                habitRepository,
                emailNotificationService
        );

        return new NotificationController(sendHabitReminder);
    }
}
