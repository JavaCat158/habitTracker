package uselogic_test;

import ru.yalab.entity.User;
import ru.yalab.entity.UserRole;
import ru.yalab.repository.UserRepository;
import ru.yalab.uselogic.RegisterUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class RegisterUserTest {

    private UserRepository userRepository;
    private RegisterUser registerUser;

    @BeforeEach
    void setUp() {
        // Создаем mock-объект для UserRepository
        userRepository = Mockito.mock(UserRepository.class);
        // Инициализируем RegisterUser с mock-репозиторием
        registerUser = new RegisterUser(userRepository);
    }

    @Test
    void testExecute_ShouldReturnTrue_WhenEmailIsUnique() {
        // Подготавливаем тестовые данные
        String username = "user1";
        String email = "user1@example.com";
        String password = "password123";


        // Настраиваем mock-репозиторий, чтобы findByEmail вернул null
        when(userRepository.findByEmail(email)).thenReturn(null);

        // Вызываем метод execute
        boolean result = registerUser.execute(username, email, password, UserRole.USER);

        // Проверяем результат
        assertTrue(result); // Ожидаем, что метод вернет true
        verify(userRepository, times(1)).findByEmail(email); // Проверяем, что findByEmail был вызван
        verify(userRepository, times(1)).save(any(User.class)); // Проверяем, что метод save был вызван
    }

    @Test
    void testExecute_ShouldReturnFalse_WhenEmailIsNotUnique() {
        // Подготавливаем тестовые данные
        String username = "user2";
        String email = "user2@example.com";
        String password = "password456";


        // Настраиваем mock-репозиторий, чтобы findByEmail вернул пользователя
        User existingUser = new User(username, email, password, UserRole.USER);
        when(userRepository.findByEmail(email)).thenReturn(existingUser);

        // Вызываем метод execute
        boolean result = registerUser.execute(username, email, password, UserRole.USER);

        // Проверяем результат
        assertFalse(result); // Ожидаем, что метод вернет false
        verify(userRepository, times(1)).findByEmail(email); // Проверяем, что findByEmail был вызван
        verify(userRepository, times(0)).save(any(User.class)); // Проверяем, что save не был вызван
    }
}

