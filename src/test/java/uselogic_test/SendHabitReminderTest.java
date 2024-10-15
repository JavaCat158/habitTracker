package uselogic_test;

import org.example.entity.Habit;
import org.example.entity.User;
import org.example.repository.HabitRepository;
import org.example.repository.UserRepository;
import org.example.services.NotificationService;
import org.example.uselogic.SendHabitReminder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SendHabitReminderTest {
    private UserRepository userRepository;
    private HabitRepository habitRepository;
    private NotificationService notificationService;
    private SendHabitReminder sendHabitReminder;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        habitRepository = mock(HabitRepository.class);
        notificationService = mock(NotificationService.class);
        sendHabitReminder = new SendHabitReminder(userRepository, habitRepository, notificationService);
    }

    @Test
    void execute_ShouldPrintUserNotFound_WhenUserDoesNotExist() {
        String userEmail = "test@example.com";

        when(userRepository.findByEmail(userEmail)).thenReturn(null);

        sendHabitReminder.execute(userEmail);

        // Verify that the notification service was not called
        verify(notificationService, never()).sendNotification(anyString(), anyString());
    }

    @Test
    void execute_ShouldPrintNoHabits_WhenUserHasNoHabits() {
        String userEmail = "test@example.com";
        User user = new User("Test User", userEmail, "password", false);

        when(userRepository.findByEmail(userEmail)).thenReturn(user);
        when(habitRepository.finaAllByUserEmail(userEmail)).thenReturn(new ArrayList<>());

        sendHabitReminder.execute(userEmail);

        // Verify that the notification service was not called
        verify(notificationService, never()).sendNotification(anyString(), anyString());
    }

    @Test
    void execute_ShouldSendNotifications_WhenUserHasHabits() {
        String userEmail = "test@example.com";
        User user = new User("Test User", userEmail, "password", false);
        List<Habit> habits = new ArrayList<>();
        habits.add(new Habit("Exercise", "30 minutes of exercise", "Daily", userEmail));
        habits.add(new Habit("Read", "Read a chapter of a book", "Daily", userEmail));

        when(userRepository.findByEmail(userEmail)).thenReturn(user);
        when(habitRepository.finaAllByUserEmail(userEmail)).thenReturn(habits);

        sendHabitReminder.execute(userEmail);

        // Capture the notification messages
        ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        // Verify that notifications were sent
        verify(notificationService, times(habits.size())).sendNotification(emailCaptor.capture(), messageCaptor.capture());

        // Check the messages sent
        List<String> sentMessages = messageCaptor.getAllValues();
        assertEquals("Напоминание: Exercise", sentMessages.get(0));
        assertEquals("Напоминание: Read", sentMessages.get(1));
    }
}
