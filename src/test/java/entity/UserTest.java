package entity;

import ru.yalab.entity.User;
import ru.yalab.entity.UserRole;
import ru.yalab.entity.UserStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserCreation() {
        User user = new User("Test User", "test@example.com", "password", UserRole.USER);
        assertEquals("Test User", user.getName());
        assertEquals("test@example.com", user.getEmail());
        assertEquals(UserRole.USER, user.getRole());  // Проверяем, что роль - USER
        assertEquals(UserStatus.ACTIVE, user.getStatus());  // По умолчанию статус должен быть ACTIVE
    }

    @Test
    void testBlockUser() {
        User user = new User("Test User", "test@example.com", "password", UserRole.USER);
        user.blockUser(); // Блокируем пользователя
        assertEquals(UserStatus.BLOCKED, user.getStatus());  // Проверяем, что статус изменился на BLOCKED
    }

    @Test
    void testUnblockUser() {
        User user = new User("Test User", "test@example.com", "password", UserRole.USER);
        user.blockUser(); // Сначала блокируем
        user.unblockUser(); // Теперь разблокируем
        assertEquals(UserStatus.ACTIVE, user.getStatus());  // Проверяем, что статус снова стал ACTIVE
    }

    @Test
    void testSetName() {
        User user = new User("Old Name", "test@example.com", "password", UserRole.USER);
        user.setName("New Name"); // Меняем имя пользователя
        assertEquals("New Name", user.getName()); // Проверяем, что имя изменилось
    }
}
