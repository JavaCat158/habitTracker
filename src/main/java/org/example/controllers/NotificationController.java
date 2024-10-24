package org.example.controllers;
/**
 * Контроллер для отправки уведомлений пользователю о необходимости выполнения привычки
 * этот класс использует бизнес-логику для отправки напоминаний пользователям
 * по электронной почте
 */

import org.example.uselogic.SendHabitReminder;

public class NotificationController {
    private final SendHabitReminder sendHabitReminder;

    /**
     * Конструктор для создания обьекта NotificationController
     *
     * @param sendHabitReminder обьект отвечающий за бизнес-логику отправки напоминаний
     */
    public NotificationController(SendHabitReminder sendHabitReminder) {
        this.sendHabitReminder = sendHabitReminder;
    }

    /**
     * Отправляет пользователю по его email
     * напоминание о необходимости выполнения привычки
     *
     * @param email электронная почта пользователя, которому нужно отправить напоминание.
     */
    public void sendNotification(String email) {
        sendHabitReminder.execute(email);
    }
}
