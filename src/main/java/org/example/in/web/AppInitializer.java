package org.example.in.web;

import org.example.controllers.NotificationController;
import org.example.out.persistence.InMemoryHabitRepository;
import org.example.out.persistence.InMemoryUserRepository;
import org.example.services.EmailNotificationService;
import org.example.uselogic.SendHabitReminder;

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
