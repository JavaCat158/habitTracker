package uselogic_test;

import ru.yalab.entity.Habit;
import ru.yalab.repository.HabitRepository;
import ru.yalab.uselogic.DeleteHabit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteHabitTest {

    private HabitRepository habitRepository;
    private DeleteHabit deleteHabit;

    @BeforeEach
    void setUp() {
        // Создаем мок репозитория привычек
        habitRepository = Mockito.mock(HabitRepository.class);
        deleteHabit = new DeleteHabit(habitRepository);
    }

    @Test
    void testExecute_HabitExists_ShouldDeleteHabit() {
        // Создаем мок для существующей привычки
        Habit habit = new Habit("Exercise", "Daily exercise", "daily", "john@example.com");

        // Настраиваем репозиторий, чтобы он возвращал привычку
        when(habitRepository.findByTitle("john@example.com", "Exercise")).thenReturn(habit);

        // Вызываем метод удаления привычки
        boolean result = deleteHabit.execute("john@example.com", "Exercise");

        // Проверяем, что метод удаления был вызван один раз
        verify(habitRepository, times(1)).deleteByTitle("john@example.com", "Exercise");

        // Проверяем, что результат метода — true (успешное удаление)
        assertTrue(result);
    }

    @Test
    void testExecute_HabitDoesNotExist_ShouldReturnFalse() {
        // Настраиваем репозиторий, чтобы он не возвращал привычку (null)
        when(habitRepository.findByTitle("john@example.com", "NonExistentHabit")).thenReturn(null);

        // Вызываем метод удаления привычки
        boolean result = deleteHabit.execute("john@example.com", "NonExistentHabit");

        // Проверяем, что метод удаления не был вызван
        verify(habitRepository, never()).deleteByTitle(anyString(), anyString());

        // Проверяем, что результат метода — false (привычка не найдена)
        assertFalse(result);
    }
}

