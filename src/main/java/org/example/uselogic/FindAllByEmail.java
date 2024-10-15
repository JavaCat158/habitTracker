package org.example.uselogic;

import org.example.entity.Habit;
import org.example.repository.HabitRepository;

import java.util.List;

/**
 * Класс FindAllByEmail используется для поиска всех привычек по email пользователя.
 *
 * Поля:
 * - habitRepository: репозиторий привычек, используемый для доступа к данным о привычках.
 *
 * Методы:
 * - findAllByEmail(String email): возвращает список всех привычек, связанных с данным email.
 *
 * @param email адрес электронной почты пользователя, для которого необходимо найти все привычки.
 * @return список объектов Habit, соответствующих данному пользователю. Если привычки не найдены, возвращается пустой список.
 */
public class FindAllByEmail {
    HabitRepository habitRepository;

    /**
     * Конструктор класса FindAllByEmail.
     *
     * @param habitRepository репозиторий привычек, используемый для доступа к данным.
     */
    public FindAllByEmail(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }

    /**
     * Метод для поиска всех привычек по email пользователя.
     *
     * @param email адрес электронной почты пользователя.
     * @return список привычек (List<Habit>), связанных с данным email.
     */
    public List<Habit> findAllByEmail(String email) {
        return habitRepository.finaAllByUserEmail(email);
    }
}
