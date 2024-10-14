package org.example.out.persistence;

import org.example.entity.Habit;
import org.example.repository.HabitRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс InMemoryHabitRepository реализует интерфейс HabitRepository и предоставляет методы
 * для управления привычками, которые хранятся в памяти.
 * <p>
 * Методы включают:
 * - Сохранение новой привычки.
 * - Поиск привычки по названию и email пользователя.
 * - Удаление привычки по названию и email пользователя.
 * - Поиск всех привычек пользователя по его email.
 * - Вывод всех сохраненных привычек в консоль.
 * - Обновление привычки по параметрам: email пользователя, старое название, новое название, описание и частота выполнения.
 */

public class InMemoryHabitRepository implements HabitRepository {
    private final List<Habit> habits = new ArrayList<>();

    @Override
    public void save(Habit habit) {
        habits.add(habit);
    }

    @Override
    public void deleteAll(String userMail) {
        habits.removeIf(habit -> habit.getUserEmail().equals(userMail));
    }

    @Override
    public Habit findByTitle(String userEmail, String title) {
        return habits.stream()
                .filter(habit -> habit.getUserEmail().equals(userEmail) && habit.getTitle().equals(title))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteByTitle(String userMail, String title) {
        habits.removeIf(habit -> habit.getUserEmail().equals(userMail) && habit.getTitle().equals(title));
    }

    // Метод поиска привычек по email пользователя
    @Override
    public List<Habit> finaAllByUserEmail(String email) {
        return habits.stream()
                .filter(habit -> habit.getUserEmail().equals(email))
                .collect(Collectors.toList());
    }

    @Override
    public void findAll() {
        System.out.println(habits);
    }

    @Override
    public boolean updateHabit(String userEmail,
                               String oldTitle,
                               String newTitle,
                               String newDescription,
                               String newFrequency) {
        Habit habit = findByTitle(userEmail, oldTitle);
        if (habit != null) {
            habit.setTitle(newTitle);
            habit.setDescription(newDescription);
            habit.setFrequency(newFrequency);
            return true;
        }
        return false;
    }
}
