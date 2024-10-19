package uselogic_test;

import ru.yalab.entity.Habit;
import ru.yalab.repository.HabitRepository;
import ru.yalab.uselogic.GetHabitStatistic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GetHabitStatisticTest {

    private HabitRepository habitRepository;
    private GetHabitStatistic getHabitStatistic;

    @BeforeEach
    void setUp() {
        // Создаем mock-объект для HabitRepository
        habitRepository = Mockito.mock(HabitRepository.class);
        // Инициализируем GetHabitStatistic с mock-репозиторием
        getHabitStatistic = new GetHabitStatistic(habitRepository);
    }

    @Test
    void testGetCompletedCount_ShouldReturnCorrectCount() {
        // Подготавливаем тестовые данные (даты выполнения привычки)
        List<LocalDate> completedDates = Arrays.asList(
                LocalDate.of(2024, 10, 1),
                LocalDate.of(2024, 10, 5),
                LocalDate.of(2024, 10, 10),
                LocalDate.of(2024, 10, 15)
        );

        // Создаем mock-объект Habit
        Habit mockHabit = Mockito.mock(Habit.class);
        when(mockHabit.getCompletedDates()).thenReturn(completedDates);

        // Задаем поведение mock-репозитория для метода findByTitle
        when(habitRepository.findByTitle("user@example.com", "My Habit")).thenReturn(mockHabit);

        // Определяем период времени для подсчета
        LocalDate start = LocalDate.of(2024, 10, 3);
        LocalDate end = LocalDate.of(2024, 10, 10);

        // Вызываем метод getCompletedCount
        int count = getHabitStatistic.getCompletedCount("user@example.com", "My Habit", start, end);

        // Проверяем результат
        assertEquals(2, count); // Ожидаем, что 2 даты (2024-10-05 и 2024-10-10) находятся в заданном диапазоне

        // Проверяем, что метод findByTitle был вызван один раз
        verify(habitRepository, times(1)).findByTitle("user@example.com", "My Habit");
    }

    @Test
    void testGetCompletedCount_ShouldReturnZero_WhenHabitNotFound() {
        // Задаем поведение mock-репозитория для метода findByTitle (возвращаем null)
        when(habitRepository.findByTitle("user@example.com", "Non-existent Habit")).thenReturn(null);

        // Определяем период времени для подсчета
        LocalDate start = LocalDate.of(2024, 10, 3);
        LocalDate end = LocalDate.of(2024, 10, 10);

        // Вызываем метод getCompletedCount
        int count = getHabitStatistic.getCompletedCount("user@example.com", "Non-existent Habit", start, end);

        // Проверяем результат
        assertEquals(0, count); // Ожидаем, что метод вернет 0, если привычка не найдена

        // Проверяем, что метод findByTitle был вызван один раз
        verify(habitRepository, times(1)).findByTitle("user@example.com", "Non-existent Habit");
    }
}
