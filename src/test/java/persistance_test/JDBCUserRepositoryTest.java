package persistance_test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.yalab.entity.User;
import ru.yalab.entity.UserRole;
import ru.yalab.entity.UserStatus;
import ru.yalab.out.database.DataBaseTemplate;
import ru.yalab.out.persistence.JDBCUserRepository;

import java.sql.SQLException;
import java.util.LinkedHashSet;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class JDBCUserRepositoryTest {


    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testdb")
            .withUsername("user")
            .withPassword("password");

    private static JDBCUserRepository userRepository;
    private static DataBaseTemplate dataBaseTemplate;

    @BeforeAll
    static void setupDatabase() throws SQLException {
        // Инициализация DataBaseTemplate с параметрами контейнера
        dataBaseTemplate = new DataBaseTemplate(postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword());

        userRepository = new JDBCUserRepository(dataBaseTemplate);

        // Создаем таблицу users
        try (var connection = dataBaseTemplate.getConnection();
             var stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE users (" +
                    "id SERIAL PRIMARY KEY," +
                    "name VARCHAR(255)," +
                    "email VARCHAR(255) UNIQUE," +
                    "password VARCHAR(255)," +
                    "role VARCHAR(50)," +
                    "status VARCHAR(50)" +
                    ");");
        }
    }

    @AfterEach
    void cleanup() throws SQLException {
        try (var connection = dataBaseTemplate.getConnection();
             var stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM users;");
        }
    }

    @Test
    void testAddUser() throws SQLException {
        User user = new User(1L, "Test User", "test@example.com", "password", UserRole.USER, UserStatus.ACTIVE);
        userRepository.addUser(user);

        User fetchedUser = userRepository.findByEmail("test@example.com");
        assertNotNull(fetchedUser);
        assertEquals(user.getName(), fetchedUser.getName());
        assertEquals(user.getEmail(), fetchedUser.getEmail());
    }

    @Test
    void testFindByEmail_UserExists() throws SQLException {
        User user = new User(1L, "Test User", "test@example.com", "password", UserRole.USER, UserStatus.ACTIVE);
        userRepository.addUser(user);

        User fetchedUser = userRepository.findByEmail("test@example.com");
        assertNotNull(fetchedUser);
        assertEquals("Test User", fetchedUser.getName());
    }

    @Test
    void testFindByEmail_UserDoesNotExist() throws SQLException {
        User fetchedUser = userRepository.findByEmail("nonexistent@example.com");
        assertNull(fetchedUser);
    }

    @Test
    void testDeleteByEmail() throws SQLException {
        User user = new User(1L, "Test User", "test@example.com", "password", UserRole.USER, UserStatus.ACTIVE);
        userRepository.addUser(user);

        userRepository.deleteByEmail("test@example.com");
        User fetchedUser = userRepository.findByEmail("test@example.com");
        assertNull(fetchedUser);
    }

    @Test
    void testFindAll() throws SQLException {
        User user1 = new User(1L, "User One", "user1@example.com", "password1", UserRole.USER, UserStatus.ACTIVE);
        User user2 = new User(2L, "User Two", "user2@example.com", "password2", UserRole.ADMIN, UserStatus.ACTIVE);
        userRepository.addUser(user1);
        userRepository.addUser(user2);

        LinkedHashSet<User> users = userRepository.findAll();
        assertEquals(2, users.size());
    }

    @Test
    void testUpdateUserProfile() throws SQLException {
        User user = new User(1L, "Original Name", "test@example.com", "password", UserRole.USER, UserStatus.ACTIVE);
        userRepository.addUser(user);

        user.setName("Updated Name");
        user.setRole(UserRole.ADMIN);
        user.setStatus(UserStatus.BLOCKED);
        userRepository.updateUserProfile(user);

        User updatedUser = userRepository.findByEmail("test@example.com");
        assertNotNull(updatedUser);
        assertEquals("Updated Name", updatedUser.getName());
        assertEquals(UserRole.ADMIN, updatedUser.getRole());
        assertEquals(UserStatus.BLOCKED, updatedUser.getStatus());
    }

    @Test
    void testBlockUser() throws SQLException {
        User user = new User(1L, "Test User", "test@example.com", "password", UserRole.USER, UserStatus.ACTIVE);
        userRepository.addUser(user);

        userRepository.blockUser("test@example.com");
        User blockedUser = userRepository.findByEmail("test@example.com");
        assertNotNull(blockedUser);
        assertEquals(UserStatus.BLOCKED, blockedUser.getStatus());
    }
}

