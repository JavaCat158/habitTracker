package uselogic_test;

import ru.yalab.entity.Habit;
import ru.yalab.repository.HabitRepository;
import ru.yalab.uselogic.CreateHabit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;

import static org.mockito.Mockito.*;

class CreateHabitTest {

    private HabitRepository habitRepository;
    private CreateHabit createHabit;

    @BeforeEach
    void setUp() {
        // Создаем мок репозитория привычек
        habitRepository = Mockito.mock(HabitRepository.class);
        createHabit = new CreateHabit(habitRepository);
    }

    @Test
    void testExecute_CreateHabitSuccess() throws SQLException {
        // Вызов метода создания привычки
        createHabit.execute("Exercise", "Daily morning exercise", "daily", "john@example.com");

        // Проверяем, что метод сохранения привычки был вызван один раз
        verify(habitRepository, times(1)).save(any(Habit.class));

        // Можно дополнительно проверить, что привычка создается с правильными параметрами
        verify(habitRepository).save(argThat(habit ->
                habit.getTitle().equals("Exercise") &&
                        habit.getDescription().equals("Daily morning exercise") &&
                        habit.getFrequency().equals("daily") &&
                        habit.getUserEmail().equals("john@example.com")
        ));
    }

    @Test
    void testGetAllHabits() throws SQLException {
        // Вызов метода получения всех привычек
        createHabit.getAllHabits();

        // Проверяем, что метод findAll был вызван один раз
        verify(habitRepository, times(1)).findAll();
    }
}
