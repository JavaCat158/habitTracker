package org.example.uselogic;

import org.example.entity.Habit;
import org.example.repository.HabitRepository;

import java.time.LocalDate;

/**
 * Класс GetHabitStatistic предоставляет логику для получения статистики по привычкам.
 *
 * Поля:
 * - habitRepository: репозиторий привычек, используемый для доступа к данным о привычках.
 *
 * Методы:
 * - getCompletedCount(String email, String title, LocalDate start, LocalDate end):
 * Возвращает количество выполненных дней для указанной привычки в заданный период.
 *
 * @param email электронная почта пользователя, которому принадлежит привычка.
 * @param title название привычки.
 * @param start дата начала периода.
 * @param end дата окончания периода.
 * @return количество выполненных дней в указанном периоде; если привычка не найдена, возвращает 0.
 */

public class GetHabitStatistic {
    private final HabitRepository habitRepository;

    public GetHabitStatistic(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }

    public int getCompletedCount(String email, String title, LocalDate start, LocalDate end) {
        Habit habit =habitRepository.findByTitle(email, title);
        if(habit != null) {
            return (int) habit.getCompletedDates().stream()
                    .filter(date -> !date.isBefore(start) && !date.isAfter(end))
                    .count();
        }
        return 0;
    }
}
