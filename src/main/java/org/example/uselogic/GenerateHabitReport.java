package org.example.uselogic;

import org.example.entity.Habit;
import org.example.repository.HabitRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Класс GenerateHabitReport предоставляет логику для генерации отчетов о привычках пользователей.
 *
 * Поля:
 * - habitRepository: репозиторий привычек, используемый для доступа к данным о привычках.
 *
 * Методы:
 * - execute(String userEmail):
 * Генерирует отчет о привычках для указанного адреса электронной почты пользователя.
 * Отчет включает название привычки, количество дней выполнения (стрик) и процент выполнения привычек за последний месяц.
 *
 * - calculateSuccessRate(Habit habit):
 * Вычисляет процент выполнения привычки за последний месяц.
 * Возвращает значение в диапазоне от 0 до 100.
 *
 * @param userEmail адрес электронной почты пользователя для получения отчетов о привычках.
 * @param habit экземпляр привычки, для которого вычисляется процент выполнения.
 * @return процент выполнения привычки.
 */

public class GenerateHabitReport {
    private final HabitRepository habitRepository;

    public GenerateHabitReport(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }

    public void execute(String userEmail) {
        List<Habit> habits = habitRepository.finaAllByUserEmail(userEmail);
        System.out.println("Отчет привычек: ");
        for(Habit habit : habits) {
            int streak = habit.calculatedStreak();
            double successRate = calculateSuccessRate(habit);
            System.out.println("Привычка: " + habit.getTitle());
            System.out.println("Частота выполнения: " + streak + " days");
            System.out.println("Процентное выполнения привычек: " + successRate + "%");
            System.out.println("-------------------------");
        }
    }

    public double calculateSuccessRate(Habit habit) {
        List<LocalDate> completedDates = habit.getCompletedDates();
        LocalDate startDate = LocalDate.now().minusMonths(1);
        LocalDate today = LocalDate.now();

        // Количество ожидаемых дней с начала месяца до сегодняшнего дня
        long expected = startDate.until(today).getDays() + 1; // +1, чтобы включить сегодняшний день

        // Количество дней, когда привычка была выполнена
        long actual = completedDates.stream()
                .filter(date -> (date.isAfter(startDate) || date.isEqual(startDate)) && (date.isBefore(today) || date.isEqual(today)))
                .count();

        return expected > 0 ? (double) actual / expected * 100 : 0.0;
    }
}
