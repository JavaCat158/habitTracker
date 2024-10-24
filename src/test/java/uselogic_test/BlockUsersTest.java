package uselogic_test;

import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.uselogic.BlockUsers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlockUsersTest {

    private UserRepository userRepository;
    private BlockUsers blockUsers;

    @BeforeEach
    void setUp() {
        // Создаем мок репозитория пользователей
        userRepository = Mockito.mock(UserRepository.class);
        blockUsers = new BlockUsers(userRepository);
    }

    @Test
    void testBlockUserSuccess() {
        // Создаем пользователя для теста
        User user = new User("John Doe", "john@example.com", "password123", false);

        // Настраиваем репозиторий для возврата пользователя
        when(userRepository.findByEmail("john@example.com")).thenReturn(user);

        // Проверяем успешную блокировку
        boolean result = blockUsers.execute("john@example.com");
        assertTrue(result);

        // Проверяем, что метод блокировки был вызван у пользователя
        verify(userRepository).findByEmail("john@example.com");
        assertTrue(user.isBloced()); // Проверка, что пользователь заблокирован
    }

    @Test
    void testBlockUserNotFound() {
        // Настраиваем репозиторий для возврата null, если пользователя не существует
        when(userRepository.findByEmail("notfound@example.com")).thenReturn(null);

        // Проверяем, что блокировка не удалась, так как пользователь не найден
        boolean result = blockUsers.execute("notfound@example.com");
        assertFalse(result);

        // Проверяем, что вызов метода findByEmail произошел
        verify(userRepository).findByEmail("notfound@example.com");
    }
}
