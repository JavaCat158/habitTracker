package ru.yalab.uselogic;

import ru.yalab.entity.Habit;
import ru.yalab.repository.HabitRepository;

import java.sql.SQLException;
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

    public boolean execute(String email, long id) throws SQLException {
        Habit habit = habitRepo.findByTitle(email, id);

        if (habit != null) {
            habit.markCompleted(LocalDate.now());
            return true;
        }

        return false;
    }
}
