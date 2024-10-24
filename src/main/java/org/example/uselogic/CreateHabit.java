package org.example.uselogic;

import org.example.entity.Habit;
import org.example.repository.HabitRepository;

/**
 * Класс CreateHabit предоставляет логику для создания привычек.
 *
 * Поля:
 * - habitRepository: репозиторий привычек, используемый для доступа к данным привычек.
 *
 * Методы:
 * - execute(String title, String description, String frequency, String userEmail):
 * Создает новую привычку с заданными параметрами и сохраняет ее в репозитории.
 *
 * - getAllHabits(): выводит все привычки, хранящиеся в репозитории.
 */

public class CreateHabit {
    private final HabitRepository habitRepository;

    public CreateHabit(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }

    public void execute(String title, String description, String frequency, String userEmail) {
        Habit habit = new Habit(title, description, frequency, userEmail);
        habitRepository.save(habit);
    }

    public void getAllHabits() {
        habitRepository.findAll();
    }
}
