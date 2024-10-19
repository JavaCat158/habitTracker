package ru.yalab.services;

/**
 * Интерфейс NotificationService определяет контракт для отправки уведомлений.
 * <p>
 * Методы:
 * - sendNotification(String email, String message): отправляет уведомление на указанный адрес электронной почты с заданным сообщением.
 */

public interface NotificationService {
    void sendNotification(String email, String message);
}
