package uselogic_test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.yalab.entity.User;
import ru.yalab.entity.UserRole;
import ru.yalab.repository.UserRepository;
import ru.yalab.uselogic.GetAllUsers;

import java.util.LinkedHashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class GetAllUsersTest {

    private UserRepository userRepository;
    private GetAllUsers getAllUsers;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);  // Мокаем UserRepository
        getAllUsers = new GetAllUsers(userRepository);         // Передаем мок в GetAllUsers
    }

    @Test
    void shouldReturnAllUsersInOrder() {
        // Создаем тестовые данные - пользователей
        User user1 = new User("email1@test.com", "User One", "password1", UserRole.USER);
        User user2 = new User("email2@test.com", "User Two", "password2", UserRole.USER);
        User user3 = new User("email3@test.com", "User Three", "password3", UserRole.USER);

        // Создаем LinkedHashSet с пользователями в определенном порядке
        LinkedHashSet<User> expectedUsers = new LinkedHashSet<>();
        expectedUsers.add(user1);
        expectedUsers.add(user2);
        expectedUsers.add(user3);

        // Настраиваем мок, чтобы возвращал этот LinkedHashSet при вызове findAll()
        when(userRepository.findAll()).thenReturn(expectedUsers);

        // Вызываем метод execute() и получаем результат
        LinkedHashSet<User> actualUsers = getAllUsers.execute();

        // Проверяем, что результат содержит всех пользователей в правильном порядке
        assertEquals(expectedUsers, actualUsers, "Возвращенные пользователи не совпадают с ожидаемыми");

        // Убедимся, что findAll() был вызван ровно один раз
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnEmptySetWhenNoUsers() {
        // Настраиваем мок, чтобы возвращал пустой LinkedHashSet
        when(userRepository.findAll()).thenReturn(new LinkedHashSet<>());

        // Вызываем метод execute() и получаем результат
        LinkedHashSet<User> actualUsers = getAllUsers.execute();

        // Проверяем, что результат - пустой набор
        assertTrue(actualUsers.isEmpty(), "Ожидался пустой набор пользователей");

        // Убедимся, что findAll() был вызван ровно один раз
        verify(userRepository, times(1)).findAll();
    }
}
