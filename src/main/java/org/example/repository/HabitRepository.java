package org.example.repository;

import org.example.entity.Habit;

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
    void save(Habit habit);

    void deleteAll(String userMail);

    Habit findByTitle(String mail, String title);

    void deleteByTitle(String userMail, String title);

    List<Habit> finaAllByUserEmail(String userMail);

    void findAll();

    boolean updateHabit(String email,
                        String oldTitle,
                        String newTitle,
                        String newDescription,
                        String newFrequency);
}
