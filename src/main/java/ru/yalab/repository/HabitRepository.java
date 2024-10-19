package ru.yalab.repository;

import ru.yalab.entity.Habit;

import java.sql.SQLException;
import java.util.List;

/**
 * Интерфейс HabitRepository определяет контракт для работы с объектами привычек (Habit).
 * Он включает методы для управления привычками, такими как сохранение, поиск, удаление и обновление.
 *
 * Методы включают:
 * - Сохранение привычки.
 * - Поиск привычки по заголовку (названию) и email пользователя.
 * - Удаление привычки по заголовку (названию) и email пользователя.
 * - Получение всех привычек для указанного email пользователя.
 * - Получение всех привычек (вывод в консоль).
 * - Обновление существующей привычки с новыми значениями.
 */
public interface HabitRepository {
    /**
     * Сохраняет новую привычку.
     *
     * @param habit объект Habit, который нужно сохранить
     */
    void save(Habit habit) throws SQLException;

    /**
     * Удаляет все привычки пользователя по его email.
     *
     * @param userMail email пользователя
     */
    void deleteAll(String userMail) throws SQLException;

    /**
     * Находит привычку по заголовку и email пользователя.
     *
     * @param userMail email пользователя
     * @param title    заголовок привычки
     * @return найденная привычка или null, если не найдена
     */
    Habit findByTitle(String userMail, String title) throws SQLException;

    /**
     * Удаляет привычку по заголовку и email пользователя.
     *
     * @param userMail email пользователя
     * @param title    заголовок привычки
     */
    void deleteByTitle(String userMail, String title) throws SQLException;

    /**
     * Находит все привычки пользователя по его email.
     *
     * @param userMail email пользователя
     * @return список привычек
     */
    List<Habit> findAllByUserEmail(String userMail) throws SQLException;

    /**
     * Выводит все привычки в консоль.
     */
    void findAll() throws SQLException;

    /**
     * Обновляет существующую привычку с новыми значениями.
     *
     * @param userEmail       email пользователя
     * @param oldTitle        старый заголовок привычки
     * @param newTitle        новый заголовок привычки
     * @param newDescription   новое описание привычки
     * @param newFrequency     новая частота привычки
     * @return true, если обновление прошло успешно, иначе false
     */
    boolean updateHabit(String userEmail,
                        String oldTitle,
                        String newTitle,
                        String newDescription,
                        String newFrequency) throws SQLException;
}
