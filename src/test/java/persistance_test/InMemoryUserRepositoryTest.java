package persistance_test;

import ru.yalab.entity.User;
import ru.yalab.entity.UserRole;
import ru.yalab.out.persistence.InMemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserRepositoryTest {

    private InMemoryUserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new InMemoryUserRepository();
    }

    @Test
    void testSaveUser() throws SQLException {
        User user = new User("John Doe", "john@example.com", "password123", UserRole.USER);
        userRepository.save(user);

        // Проверяем, что пользователь сохранен
        User foundUser = userRepository.findByEmail("john@example.com");
        assertNotNull(foundUser);
        assertEquals("John Doe", foundUser.getName());
        assertEquals("john@example.com", foundUser.getEmail());
        assertEquals("password123", foundUser.getPassword());
        assertTrue(foundUser.getRole().equals(UserRole.USER));
    }

    @Test
    void testFindUserByEmail() throws SQLException {
        User user1 = new User("User One", "user1@example.com", "password1", UserRole.USER);
        User user2 = new User("User Two", "user2@example.com", "password2", UserRole.USER);
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
    void testDeleteUserByEmail() throws SQLException {
        User user = new User("John Doe", "john@example.com", "password123", UserRole.USER);
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
    void testFindAllUsers() throws SQLException {
        User user1 = new User("User One", "user1@example.com", "password1", UserRole.USER);
        User user2 = new User("User Two", "user2@example.com", "password2", UserRole.ADMIN);
        userRepository.save(user1);
        userRepository.save(user2);

        // Получаем всех пользователей
        LinkedHashSet<User> users = userRepository.findAll();
        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }
}
