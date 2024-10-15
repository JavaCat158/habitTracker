package uselogic_test;

import org.example.entity.Habit;
import org.example.repository.HabitRepository;
import org.example.uselogic.GenerateHabitReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GenerateHabitReportTest {

    private HabitRepository habitRepository;
    private GenerateHabitReport generateHabitReport;

    @BeforeEach
    void setUp() {
        // Создаем мок репозитория привычек
        habitRepository = Mockito.mock(HabitRepository.class);
        generateHabitReport = new GenerateHabitReport(habitRepository);
    }

    @Test
    void testExecute_UserHasHabits_ShouldGenerateReport() {
        // Создаем список привычек для пользователя
        Habit habit1 = new Habit("Workout", "Daily workout", "daily", "user@example.com");
        habit1.addCompletedDate(LocalDate.now().minusDays(1));
        habit1.addCompletedDate(LocalDate.now().minusDays(2));

        Habit habit2 = new Habit("Read", "Read a book", "daily", "user@example.com");
        habit2.addCompletedDate(LocalDate.now().minusDays(3));

        List<Habit> habits = Arrays.asList(habit1, habit2);

        // Настраиваем мок репозитория, чтобы он возвращал список привычек
        when(habitRepository.finaAllByUserEmail("user@example.com")).thenReturn(habits);

        // Вызываем метод генерации отчета
        generateHabitReport.execute("user@example.com");

        // Проверяем, что репозиторий был вызван для получения привычек
        verify(habitRepository, times(1)).finaAllByUserEmail("user@example.com");
    }

    @Test
    void testExecute_UserHasNoHabits_ShouldGenerateEmptyReport() {
        // Настраиваем мок репозитория, чтобы он возвращал пустой список
        when(habitRepository.finaAllByUserEmail("user@example.com")).thenReturn(Collections.emptyList());

        // Вызываем метод генерации отчета
        generateHabitReport.execute("user@example.com");

        // Проверяем, что репозиторий был вызван для получения привычек
        verify(habitRepository, times(1)).finaAllByUserEmail("user@example.com");
    }

    @Test
    void testCalculateSuccessRate_ShouldReturnCorrectPercentage() {
        // Создаем привычку и добавляем выполненные даты
        Habit habit = new Habit("Workout", "Daily workout", "daily", "user@example.com");
        habit.addCompletedDate(LocalDate.now().minusDays(1));
        habit.addCompletedDate(LocalDate.now().minusDays(2));
        habit.addCompletedDate(LocalDate.now().minusDays(3));

        // Проверяем правильность вычисления процента выполнения
        double successRate = generateHabitReport.calculateSuccessRate(habit);

        // Ожидаемое значение: 3 выполненные дня за последний месяц (например, 30 дней)
        long expected = 3;
        long totalDaysInMonth = LocalDate.now().minusMonths(1).until(LocalDate.now()).getDays() + 1;
        double expectedRate = (double) expected / totalDaysInMonth * 100;

        assertEquals(expectedRate, successRate, 0.01);
    }
}

