package org.example.uselogic;

import org.example.entity.Habit;
import org.example.repository.HabitRepository;

import java.time.LocalDate;

/**
 * Класс MarkHabitAsCompleted реализует логику для отметки привычки как выполненной.
 *
 * Поля:
 * - habitRepo: репозиторий привычек, используемый для доступа к данным о привычках.
 *
 * Методы:
 * - execute(String email, String title):
 * Отмечает привычку с указанным названием как выполненную для пользователя с заданным адресом электронной почты.
 *
 * @param email адрес электронной почты пользователя.
 * @param title название привычки, которую нужно отметить как выполненную.
 * @return true, если привычка успешно отмечена как выполненная; false, если привычка не найдена.
 */

public class MarkHabitAsCompleted {
    private final HabitRepository habitRepo;

    public MarkHabitAsCompleted(HabitRepository habitRepo) {
        this.habitRepo = habitRepo;
    }

    public boolean execute(String email, String title) {
        Habit habit = habitRepo.findByTitle(email, title);

        if (habit != null) {
            habit.markCompleted(LocalDate.now());
            return true;
        }

        return false;
    }
}
