package uselogic_test;

import org.example.entity.Habit;
import org.example.repository.HabitRepository;
import org.example.uselogic.MarkHabitAsCompleted;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.example.entity.Habit;
import org.example.repository.HabitRepository;
import org.example.uselogic.MarkHabitAsCompleted;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class MarkHabitAsCompletedTest {

    private HabitRepository habitRepository;
    private MarkHabitAsCompleted markHabitAsCompleted;

    @BeforeEach
    void setUp() {
        // Создаем mock-объект для HabitRepository
        habitRepository = Mockito.mock(HabitRepository.class);
        // Инициализируем MarkHabitAsCompleted с mock-репозиторием
        markHabitAsCompleted = new MarkHabitAsCompleted(habitRepository);
    }

    @Test
    void testExecute_ShouldReturnTrue_WhenHabitIsFound() {
        // Подготавливаем тестовые данные
        String email = "user@example.com";
        String title = "Exercise";

        // Создаем mock-объект Habit
        Habit mockHabit = Mockito.mock(Habit.class);
        when(habitRepository.findByTitle(email, title)).thenReturn(mockHabit);

        // Вызываем метод execute
        boolean result = markHabitAsCompleted.execute(email, title);

        // Проверяем результат
        assertTrue(result); // Ожидаем, что метод вернет true
        verify(habitRepository, times(1)).findByTitle(email, title); // Проверяем, что findByTitle был вызван
        verify(mockHabit, times(1)).markCompleted(LocalDate.now()); // Убедимся, что markCompleted был вызван
    }

    @Test
    void testExecute_ShouldReturnFalse_WhenHabitIsNotFound() {
        // Подготавливаем тестовые данные
        String email = "user@example.com";
        String title = "Exercise";

        // Задаем поведение mock-репозитория для метода findByTitle (возвращаем null)
        when(habitRepository.findByTitle(email, title)).thenReturn(null);

        // Вызываем метод execute
        boolean result = markHabitAsCompleted.execute(email, title);

        // Проверяем результат
        assertFalse(result); // Ожидаем, что метод вернет false
        verify(habitRepository, times(1)).findByTitle(email, title); // Проверяем, что findByTitle был вызван
        // Здесь убираем verifyNoInteractions, так как метод был вызван
    }
}


