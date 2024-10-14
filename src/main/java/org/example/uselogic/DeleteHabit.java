package org.example.uselogic;

import org.example.entity.Habit;
import org.example.repository.HabitRepository;

/**
 * Класс DeleteHabit предоставляет логику для удаления привычек.
 *
 * Поля:
 * - habitRepository: репозиторий привычек, используемый для доступа к данным привычек.
 *
 * Методы:
 * - execute(String email, String title):
 * Находит привычку по указанному названию и адресу электронной почты пользователя.
 * Если привычка найдена, она удаляется из репозитория, и метод возвращает true.
 * В противном случае возвращается false.
 */

public class DeleteHabit {
    private HabitRepository habitRepository;

    public DeleteHabit(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }

    public boolean execute(String email, String title) {
        Habit habit = habitRepository.findByTitle(email, title);

        if (habit != null) {
            habitRepository.deleteByTitle(email, title);
            return true;
        }
        return false;
    }
}
