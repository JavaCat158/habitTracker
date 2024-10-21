/*
package uselogic_test;

import ru.yalab.repository.HabitRepository;
import ru.yalab.uselogic.UpdateHabit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class UpdateHabitTest {
    private HabitRepository habitRepo; // Мок объект для репозитория привычек
    private UpdateHabit updateHabit;   // Тестируемый класс

    @BeforeEach
    void setUp() {
        // Инициализируем мок объект и экземпляр класса UpdateHabit перед каждым тестом
        habitRepo = mock(HabitRepository.class);
        updateHabit = new UpdateHabit(habitRepo);
    }

    @Test
    void execute_ShouldReturnTrue_WhenHabitIsUpdatedSuccessfully() {
        String userEmail = "test@example.com"; // Тестовый email пользователя
        String oldTitle = "Old Habit Title";    // Старое название привычки
        String newTitle = "New Habit Title";    // Новое название привычки
        String newDescription = "New Description"; // Новое описание привычки
        String newFrequency = "Weekly";          // Новая частота привычки

        // Настраиваем поведение метода updateHabit, чтобы он возвращал true
        when(habitRepo.updateHabit(userEmail, oldTitle, newTitle, newDescription, newFrequency)).thenReturn(true);

        // Выполняем обновление привычки
        boolean result = updateHabit.execute(userEmail, oldTitle, newTitle, newDescription, newFrequency);

        // Проверяем, что результат равен true
        assertTrue(result);
        // Проверяем, что метод updateHabit был вызван один раз с правильными параметрами
        verify(habitRepo, times(1)).updateHabit(userEmail, oldTitle, newTitle, newDescription, newFrequency);
    }

    @Test
    void execute_ShouldReturnFalse_WhenHabitUpdateFails() {
        String userEmail = "test@example.com"; // Тестовый email пользователя
        String oldTitle = "Old Habit Title";    // Старое название привычки
        String newTitle = "New Habit Title";    // Новое название привычки
        String newDescription = "New Description"; // Новое описание привычки
        String newFrequency = "Weekly";          // Новая частота привычки

        // Настраиваем поведение метода updateHabit, чтобы он возвращал false
        when(habitRepo.updateHabit(userEmail, oldTitle, newTitle, newDescription, newFrequency)).thenReturn(false);

        // Выполняем обновление привычки
        boolean result = updateHabit.execute(userEmail, oldTitle, newTitle, newDescription, newFrequency);

        // Проверяем, что результат равен false
        assertFalse(result);
        // Проверяем, что метод updateHabit был вызван один раз с правильными параметрами
        verify(habitRepo, times(1)).updateHabit(userEmail, oldTitle, newTitle, newDescription, newFrequency);
    }
}

*/
