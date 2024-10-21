package ru.yalab.in.web;

import ru.yalab.controllers.NotificationController;
import ru.yalab.out.database.DataBaseTemplate;
import ru.yalab.out.persistence.JDBCHabitRepository;
import ru.yalab.out.persistence.JDBCUserRepository;
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
    DataBaseTemplate dataBaseTemplate = new DataBaseTemplate();
    public NotificationController init() {
        JDBCUserRepository userRepository = new JDBCUserRepository(dataBaseTemplate);
        JDBCHabitRepository habitRepository = new JDBCHabitRepository(dataBaseTemplate);

        EmailNotificationService emailNotificationService = new EmailNotificationService();

        SendHabitReminder sendHabitReminder = new SendHabitReminder(
                userRepository,
                habitRepository,
                emailNotificationService
        );

        return new NotificationController(sendHabitReminder);
    }
}
