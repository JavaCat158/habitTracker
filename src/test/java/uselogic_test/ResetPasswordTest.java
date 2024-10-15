package uselogic_test;

import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.uselogic.ResetPassword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ResetPasswordTest {

    private UserRepository userRepository;
    private ResetPassword resetPassword;

    @BeforeEach
    void setUp() {
        // Создаем mock-объект для UserRepository
        userRepository = Mockito.mock(UserRepository.class);
        // Инициализируем ResetPassword с mock-репозиторием
        resetPassword = new ResetPassword(userRepository);
    }

    @Test
    void testExecute_ShouldReturnTrue_WhenUserExists() {
        // Подготавливаем тестовые данные
        String email = "user@example.com";
        User user = new User("User", email, "oldPassword", false);

        // Настраиваем mock-репозиторий, чтобы findByEmail вернул пользователя
        when(userRepository.findByEmail(email)).thenReturn(user);

        // Вызываем метод execute
        boolean result = resetPassword.execute(email);

        // Проверяем результат
        assertTrue(result); // Ожидаем, что метод вернет true
        verify(userRepository, times(1)).findByEmail(email); // Проверяем, что findByEmail был вызван
        assertFalse(user.getPassword().equals("oldPassword")); // Ожидаем, что пароль изменился
    }

    @Test
    void testExecute_ShouldReturnFalse_WhenUserDoesNotExist() {
        // Подготавливаем тестовые данные
        String email = "nonexistent@example.com";

        // Настраиваем mock-репозиторий, чтобы findByEmail вернул null
        when(userRepository.findByEmail(email)).thenReturn(null);

        // Вызываем метод execute
        boolean result = resetPassword.execute(email);

        // Проверяем результат
        assertFalse(result); // Ожидаем, что метод вернет false
        verify(userRepository, times(1)).findByEmail(email); // Проверяем, что findByEmail был вызван
    }
}

