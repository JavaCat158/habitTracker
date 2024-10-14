package uselogic_test;

import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.uselogic.GetAllUsers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GetAllUsersTest {

    private UserRepository userRepository;
    private GetAllUsers getAllUsers;

    @BeforeEach
    void setUp() {
        // Создаем mock-объект для UserRepository
        userRepository = Mockito.mock(UserRepository.class);
        // Инициализируем GetAllUsers с mock-репозиторием
        getAllUsers = new GetAllUsers(userRepository);
    }

    @Test
    void testExecute_ShouldReturnAllUsers() {
        // Подготавливаем тестовые данные (список пользователей)
        List<User> mockUsers = Arrays.asList(
                new User("John Doe", "john@example.com", "password123", false),
                new User("Jane Doe", "jane@example.com", "securepass", true)
        );

        // Устанавливаем поведение mock-репозитория
        when(userRepository.findAll()).thenReturn(mockUsers);

        // Вызываем метод execute
        List<User> result = getAllUsers.execute();

        // Проверяем результат
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("jane@example.com", result.get(1).getEmail());
        assertEquals(false, result.get(0).isAdmin());
        assertEquals(true, result.get(1).isAdmin());

        // Проверяем, что метод findAll() был вызван ровно один раз
        verify(userRepository, times(1)).findAll();
    }
}
