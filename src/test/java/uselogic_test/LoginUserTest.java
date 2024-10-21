package uselogic_test;

import ru.yalab.entity.User;
import ru.yalab.entity.UserRole;
import ru.yalab.entity.UserStatus;
import ru.yalab.repository.UserRepository;
import ru.yalab.uselogic.LoginUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.management.relation.RoleStatus;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LoginUserTest {

    private UserRepository userRepository;
    private LoginUser loginUser;

    @BeforeEach
    void setUp() {
        // Создаем mock-объект для UserRepository
        userRepository = Mockito.mock(UserRepository.class);
        // Инициализируем LoginUser с mock-репозиторием
        loginUser = new LoginUser(userRepository);
    }

    @Test
    void testExecute_ShouldReturnTrue_WhenCredentialsAreValid() {
        // Подготавливаем тестовые данные
        String email = "user@example.com";
        String password = "correct_password";

        // Создаем mock-объект User
        User mockUser = Mockito.mock(User.class);

        // Устанавливаем поведение для mock-объекта
        when(mockUser.getPassword()).thenReturn(password);
        when(mockUser.getStatus()).thenReturn(UserStatus.ACTIVE); // Убеждаемся, что пользователь активен
        when(userRepository.findByEmail(email)).thenReturn(mockUser);

        // Вызываем метод execute
        boolean result = loginUser.execute(email, password);

        // Проверяем результат
        assertTrue(result); // Ожидаем, что метод вернет true

        // Проверяем, что метод findByEmail был вызван ровно один раз
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testExecute_ShouldReturnFalse_WhenUserIsBlocked() {
        // Подготавливаем тестовые данные
        String email = "user@example.com";
        String password = "any_password";

        // Создаем mock-объект User
        User mockUser = Mockito.mock(User.class);

        when(mockUser.getPassword()).thenReturn(password);
        when(mockUser.getStatus()).thenReturn(UserStatus.BLOCKED);
        when(userRepository.findByEmail(email)).thenReturn(mockUser);

        // Вызываем метод execute
        boolean result = loginUser.execute(email, password);

        // Проверяем результат
        assertFalse(result); // Ожидаем, что метод вернет false
        verify(userRepository, times(1)).findByEmail(email); // Проверяем, что findByEmail был вызван
    }

    @Test
    void testExecute_ShouldReturnFalse_WhenCredentialsAreInvalid() {
        // Подготавливаем тестовые данные
        String email = "user@example.com";
        String password = "wrong_password";

        // Создаем mock-объект User
        User mockUser = Mockito.mock(User.class);
        when(mockUser.getPassword()).thenReturn("correct_password");
        when(mockUser.getStatus()).thenReturn(UserStatus.ACTIVE);
        when(userRepository.findByEmail(email)).thenReturn(mockUser);

        // Вызываем метод execute
        boolean result = loginUser.execute(email, password);

        // Проверяем результат
        assertFalse(result); // Ожидаем, что метод вернет false
        verify(userRepository, times(1)).findByEmail(email); // Проверяем, что findByEmail был вызван
    }

    @Test
    void testExecute_ShouldReturnFalse_WhenUserNotFound() {
        // Подготавливаем тестовые данные
        String email = "user@example.com";
        String password = "any_password";

        // Задаем поведение mock-репозитория для метода findByEmail (возвращаем null)
        when(userRepository.findByEmail(email)).thenReturn(null);

        // Вызываем метод execute
        boolean result = loginUser.execute(email, password);

        // Проверяем результат
        assertFalse(result); // Ожидаем, что метод вернет false
        verify(userRepository, times(1)).findByEmail(email); // Проверяем, что findByEmail был вызван
    }

    @Test
    void testGetUser_ShouldReturnUser_WhenUserExists() {
        // Подготавливаем тестовые данные
        String email = "user@example.com";
        User mockUser = new User("John Doe", email, "password", UserRole.USER);
        when(userRepository.findByEmail(email)).thenReturn(mockUser);

        // Вызываем метод getUser
        User resultUser = loginUser.getUser(email);

        // Проверяем результат
        assertEquals(mockUser, resultUser); // Ожидаем, что возвращаемый пользователь равен mockUser
        verify(userRepository, times(1)).findByEmail(email); // Проверяем, что findByEmail был вызван
    }

    @Test
    void testGetUser_ShouldReturnNull_WhenUserNotFound() {
        // Подготавливаем тестовые данные
        String email = "user@example.com";
        when(userRepository.findByEmail(email)).thenReturn(null);

        // Вызываем метод getUser
        User resultUser = loginUser.getUser(email);

        // Проверяем результат
        assertEquals(null, resultUser); // Ожидаем, что возвращаемый пользователь будет null
        verify(userRepository, times(1)).findByEmail(email); // Проверяем, что findByEmail был вызван
    }
}

