package ru.yalab.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * class StreakIterate имеет поле список для подсчета количество повторяемых дат
 */
public class StreakIterate {
    List<LocalDate> completedDates = new ArrayList<>();

    /**
     * Рассчитывает текущий "streak" (цепочку последовательных дней, когда привычка была выполнена).
     *
     * @return длина текущей цепочки выполнения привычки
     */
    public int calculatedStreak() {
        int streak = 0;
        LocalDate today = LocalDate.now();

        for (int i = completedDates.size() - 1; i >= 0; i--) {
            LocalDate date = completedDates.get(i);
            LocalDate checkDate = today.minusDays(streak); // Переход к дате, которая ожидается в пределах стрика

            // Если разница в днях меньше или равна 24 дням, продолжаем стрик
            if (ChronoUnit.DAYS.between(date, checkDate) <= 24) {
                streak++;
            } else {
                break;
            }
        }
        return streak;
    }
}
