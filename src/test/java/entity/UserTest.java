package entity;

import org.example.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void testUserCreation() {
        User user = new User("Test User", "test@example.com", "password", false);
        assertEquals("Test User", user.getName());
        assertEquals("test@example.com", user.getEmail());
        assertFalse(user.isAdmin());
        assertFalse(user.isBloced());
    }

    @Test
    void testBlockUser() {
        User user = new User("Test User", "test@example.com", "password", false);
        user.blockUser();
        assertTrue(user.isBloced());
    }

    @Test
    void testUnblockUser() {
        User user = new User("Test User", "test@example.com", "password", false);
        user.blockUser(); // Сначала блокируем
        user.unblockUser(); // Теперь разблокируем
        assertFalse(user.isBloced());
    }

    @Test
    void testSetName() {
        User user = new User("Old Name", "test@example.com", "password", false);
        user.setName("New Name");
        assertEquals("New Name", user.getName());
    }
}

