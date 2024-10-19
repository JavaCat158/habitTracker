package persistance_test;

import ru.yalab.entity.Habit;
import ru.yalab.out.persistence.InMemoryHabitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class InMemoryHabitRepositoryTest {

    private InMemoryHabitRepository habitRepository;

    @BeforeEach
    void setUp() {
        habitRepository = new InMemoryHabitRepository();
    }

    @Test
    void testSaveHabit() {
        Habit habit = new Habit("Test Habit", "Test Description", "Daily", "user@example.com");
        habitRepository.save(habit);

        // Проверяем, что привычка успешно добавлена
        Habit foundHabit = habitRepository.findByTitle("user@example.com", "Test Habit");
        assertNotNull(foundHabit);
        assertEquals("Test Habit", foundHabit.getTitle());
        assertEquals("Test Description", foundHabit.getDescription());
        assertEquals("Daily", foundHabit.getFrequency());
        assertEquals("user@example.com", foundHabit.getUserEmail());
    }

    @Test
    void testFindHabitByTitle() {
        Habit habit1 = new Habit("Habit 1", "Description 1", "Daily", "user1@example.com");
        Habit habit2 = new Habit("Habit 2", "Description 2", "Weekly", "user2@example.com");

        habitRepository.save(habit1);
        habitRepository.save(habit2);

        // Поиск по существующему названию
        Habit foundHabit = habitRepository.findByTitle("user1@example.com", "Habit 1");
        assertNotNull(foundHabit);
        assertEquals("Habit 1", foundHabit.getTitle());

        // Поиск по несуществующему названию
        Habit notFoundHabit = habitRepository.findByTitle("user1@example.com", "Nonexistent Habit");
        assertNull(notFoundHabit);
    }

    @Test
    void testDeleteHabit() {
        Habit habit = new Habit("Test Habit", "Test Description", "Daily", "user@example.com");
        habitRepository.save(habit);

        // Проверяем, что привычка существует до удаления
        Habit foundHabit = habitRepository.findByTitle("user@example.com", "Test Habit");
        assertNotNull(foundHabit);

        // Удаление привычки
        habitRepository.deleteByTitle("user@example.com", "Test Habit");

        // Проверяем, что привычка удалена
        Habit deletedHabit = habitRepository.findByTitle("user@example.com", "Test Habit");
        assertNull(deletedHabit);
    }

    @Test
    void testUpdateHabit() {
        Habit habit = new Habit("Old Title", "Old Description", "Daily", "user@example.com");
        habitRepository.save(habit);

        // Обновляем привычку
        boolean updated = habitRepository.updateHabit("user@example.com", "Old Title", "New Title", "New Description", "Weekly");
        assertTrue(updated);

        // Проверяем, что данные привычки обновились
        Habit updatedHabit = habitRepository.findByTitle("user@example.com", "New Title");
        assertNotNull(updatedHabit);
        assertEquals("New Title", updatedHabit.getTitle());
        assertEquals("New Description", updatedHabit.getDescription());
        assertEquals("Weekly", updatedHabit.getFrequency());
    }

    @Test
    void testFindAllHabitsByUser() {
        Habit habit1 = new Habit("Habit 1", "Description 1", "Daily", "user@example.com");
        Habit habit2 = new Habit("Habit 2", "Description 2", "Weekly", "user@example.com");
        habitRepository.save(habit1);
        habitRepository.save(habit2);

        // Получаем все привычки пользователя
        List<Habit> userHabits = habitRepository.finaAllByUserEmail("user@example.com");
        assertEquals(2, userHabits.size());
    }
}
