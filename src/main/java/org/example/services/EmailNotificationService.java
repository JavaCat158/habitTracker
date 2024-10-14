package org.example.services;

/**
 * Класс EmailNotificationService реализует интерфейс NotificationService
 * и предоставляет функциональность для отправки уведомлений по электронной почте.
 *
 * Методы:
 * - sendNotification(String email, String message): отправляет уведомление на указанный адрес электронной почты с заданным сообщением.
 */

public class EmailNotificationService implements NotificationService {
    @Override
    public void sendNotification(String email, String message) {
        System.out.println("Отправка email на: " + email);
        System.out.println("Сообщение: " + message);
    }
}
