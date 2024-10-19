package entity;

import ru.yalab.entity.Habit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class HabitTest {

    private Habit habit;

    @BeforeEach
    void setUp() {
        habit = new Habit("Exercise", "Daily workout", "Daily", "user@example.com");
    }

    @Test
    void testHabitInitialization() {
        assertEquals("Exercise", habit.getTitle());
        assertEquals("Daily workout", habit.getDescription());
        assertEquals("Daily", habit.getFrequency());
        assertEquals(LocalDate.now(), habit.getCreationDate());
        assertEquals("user@example.com", habit.getUserEmail());
        assertTrue(habit.getCompletedDates().isEmpty(), "Completed dates should be empty at initialization");
    }

    @Test
    void testMarkCompleted() {
        LocalDate date = LocalDate.now();
        habit.markCompleted(date);
        assertEquals(1, habit.getCompletedDates().size());
        assertTrue(habit.getCompletedDates().contains(date));

        // Попробуем отметить ту же дату еще раз
        habit.markCompleted(date);
        assertEquals(1, habit.getCompletedDates().size(), "Should not allow duplicate completion dates");
    }
}
