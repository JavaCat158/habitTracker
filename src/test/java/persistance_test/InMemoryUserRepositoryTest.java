package persistance_test;

import org.example.entity.User;
import org.example.out.persistence.InMemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserRepositoryTest {

    private InMemoryUserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new InMemoryUserRepository();
    }

    @Test
    void testSaveUser() {
        User user = new User("John Doe", "john@example.com", "password123", false);
        userRepository.save(user);

        // Проверяем, что пользователь сохранен
        User foundUser = userRepository.findByEmail("john@example.com");
        assertNotNull(foundUser);
        assertEquals("John Doe", foundUser.getName());
        assertEquals("john@example.com", foundUser.getEmail());
        assertEquals("password123", foundUser.getPassword());
        assertFalse(foundUser.isAdmin());
    }

    @Test
    void testFindUserByEmail() {
        User user1 = new User("User One", "user1@example.com", "password1", false);
        User user2 = new User("User Two", "user2@example.com", "password2", false);
        userRepository.save(user1);
        userRepository.save(user2);

        // Поиск существующего пользователя
        User foundUser = userRepository.findByEmail("user1@example.com");
        assertNotNull(foundUser);
        assertEquals("User One", foundUser.getName());

        // Поиск несуществующего пользователя
        User notFoundUser = userRepository.findByEmail("nonexistent@example.com");
        assertNull(notFoundUser);
    }

    @Test
    void testDeleteUserByEmail() {
        User user = new User("John Doe", "john@example.com", "password123", false);
        userRepository.save(user);

        // Проверяем, что пользователь существует до удаления
        User foundUser = userRepository.findByEmail("john@example.com");
        assertNotNull(foundUser);

        // Удаление пользователя
        userRepository.deleteByEmail("john@example.com");

        // Проверяем, что пользователь удален
        User deletedUser = userRepository.findByEmail("john@example.com");
        assertNull(deletedUser);
    }

    @Test
    void testFindAllUsers() {
        User user1 = new User("User One", "user1@example.com", "password1", false);
        User user2 = new User("User Two", "user2@example.com", "password2", true);
        userRepository.save(user1);
        userRepository.save(user2);

        // Получаем всех пользователей
        List<User> users = userRepository.findAll();
        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }
}
