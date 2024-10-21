/*
package uselogic_test;

import ru.yalab.entity.User;
import ru.yalab.entity.UserRole;
import ru.yalab.repository.UserRepository;
import ru.yalab.uselogic.DeleteUserProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteUserProfileTest {

    private UserRepository userRepository;
    private DeleteUserProfile deleteUserProfile;

    @BeforeEach
    void setUp() {
        // Создаем мок репозитория пользователей
        userRepository = Mockito.mock(UserRepository.class);
        deleteUserProfile = new DeleteUserProfile(userRepository);
    }

    @Test
    void testDeleteUser_UserExists_ShouldDeleteUser() {
        // Создаем мок для существующего пользователя
        User user = new User("john@example.com", "John", "password123", UserRole.USER);

        // Настраиваем репозиторий, чтобы он возвращал пользователя
        when(userRepository.findByEmail("john@example.com")).thenReturn(user);

        // Вызываем метод удаления пользователя
        boolean result = deleteUserProfile.deleteUser("john@example.com");

        // Проверяем, что метод удаления был вызван один раз
        verify(userRepository, times(1)).deleteByEmail("john@example.com");

        // Проверяем, что результат метода — true (успешное удаление)
        assertTrue(result);
    }

    @Test
    void testDeleteUser_UserDoesNotExist_ShouldReturnFalse() {
        // Настраиваем репозиторий, чтобы он не возвращал пользователя (null)
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(null);

        // Вызываем метод удаления пользователя
        boolean result = deleteUserProfile.deleteUser("nonexistent@example.com");

        // Проверяем, что метод удаления не был вызван
        verify(userRepository, never()).deleteByEmail(anyString());

        // Проверяем, что результат метода — false (пользователь не найден)
        assertFalse(result);
    }
}

*/
