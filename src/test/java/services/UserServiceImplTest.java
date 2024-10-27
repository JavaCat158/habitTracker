package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.yalab.dto.LoginRequest;
import ru.yalab.dto.LoginResponse;
import ru.yalab.dto.UserDto;
import ru.yalab.entity.User;
import ru.yalab.entity.UserRole;
import ru.yalab.entity.UserStatus;
import ru.yalab.mapper.UserMapper;
import ru.yalab.repository.UserRepository;
import ru.yalab.services.UserServiceImpl;

import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserRepository userRepository;
    private UserServiceImpl userService;
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userMapper = UserMapper.INSTANCE;
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void testBlockUser_Success() throws SQLException {
        // Arrange
        String email = "test@example.com";
        User user = new User(1L, "Test User", email, "password", UserRole.USER, UserStatus.ACTIVE);
        when(userRepository.findByEmail(email)).thenReturn(user);

        // Act
        UserDto result = userService.blockUser(email);

        // Assert
        assertNotNull(result);
        assertEquals(UserStatus.BLOCKED, user.getStatus());
        verify(userRepository).updateUserProfile(user);
    }

    @Test
    void testBlockUser_UserNotFound() throws SQLException {
        // Arrange
        String email = "notfound@example.com";
        when(userRepository.findByEmail(email)).thenReturn(null);

        // Act
        UserDto result = userService.blockUser(email);

        // Assert
        assertNull(result);
    }

    @Test
    void testUnblockUser_Success() throws SQLException {
        // Arrange
        String email = "test@example.com";
        User user = new User(1L, "Test User", email, "password", UserRole.USER, UserStatus.BLOCKED);
        when(userRepository.findByEmail(email)).thenReturn(user);

        // Act
        UserDto result = userService.unblockUser(email);

        // Assert
        assertNotNull(result);
        assertEquals(UserStatus.ACTIVE, user.getStatus());
        verify(userRepository).updateUserProfile(user);
    }

    @Test
    void testUnblockUser_UserNotFound() throws SQLException {
        // Arrange
        String email = "notfound@example.com";
        when(userRepository.findByEmail(email)).thenReturn(null);

        // Act
        UserDto result = userService.unblockUser(email);

        // Assert
        assertNull(result);
    }

    @Test
    void testDeleteUser_Success() throws SQLException {
        // Arrange
        String email = "test@example.com";
        User user = new User(1L, "Test User", email, "password", UserRole.USER, UserStatus.ACTIVE);
        when(userRepository.findByEmail(email)).thenReturn(user);

        // Act
        UserDto result = userService.deleteUser(email);

        // Assert
        assertNotNull(result);
        verify(userRepository).deleteByEmail(email);
    }

    @Test
    void testDeleteUser_UserNotFound() throws SQLException {
        // Arrange
        String email = "notfound@example.com";
        when(userRepository.findByEmail(email)).thenReturn(null);

        // Act
        UserDto result = userService.deleteUser(email);

        // Assert
        assertNull(result);
    }

    @Test
    void testGetAllUsers() throws SQLException {
        // Arrange
        User user1 = new User(1L, "User One", "user1@example.com", "password1", UserRole.USER, UserStatus.ACTIVE);
        User user2 = new User(2L, "User Two", "user2@example.com", "password2", UserRole.USER, UserStatus.ACTIVE);
        LinkedHashSet<User> users = new LinkedHashSet<>();
        users.add(user1);
        users.add(user2);
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<UserDto> result = userService.getAllUsers();

        // Assert
        assertEquals(2, result.size());
        assertEquals(userMapper.userToUserDto(user1), result.get(0));
        assertEquals(userMapper.userToUserDto(user2), result.get(1));
    }

    @Test
    void testLogin_Success() throws SQLException {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("test@example.com", "password");
        User user = new User(1L, "Test User", "test@example.com", "password", UserRole.USER, UserStatus.ACTIVE);
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(user);

        // Act
        LoginResponse result = userService.login(loginRequest);

        // Assert
        assertEquals("Успешно", result.getStatus());
        assertEquals("Вход выполнен.", result.getMessage());
        assertNotNull(result.getUser());
    }

    @Test
    void testLogin_UserNotFound() throws SQLException {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("notfound@example.com", "password");
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(null);

        // Act
        LoginResponse result = userService.login(loginRequest);

        // Assert
        assertEquals("Ошибка", result.getStatus());
        assertEquals("Такого пользователя не существует или он был удален!", result.getMessage());
        assertNull(result.getUser());
    }

    @Test
    void testLogin_UserBlocked() throws SQLException {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("blocked@example.com", "password");
        User user = new User(1L, "Blocked User", "blocked@example.com", "password", UserRole.USER, UserStatus.BLOCKED);
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(user);

        // Act
        LoginResponse result = userService.login(loginRequest);

        // Assert
        assertEquals("Ошибка", result.getStatus());
        assertEquals("Вы заблокированы, обратитесь в поддержку!", result.getMessage());
        assertNull(result.getUser());
    }

    @Test
    void testLogin_IncorrectPassword() throws SQLException {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("test@example.com", "wrongpassword");
        User user = new User(1L, "Test User", "test@example.com", "password", UserRole.USER, UserStatus.ACTIVE);
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(user);

        // Act
        LoginResponse result = userService.login(loginRequest);

        // Assert
        assertEquals("Ошибка", result.getStatus());
        assertEquals("Неверный пароль", result.getMessage());
        assertNull(result.getUser());
    }
}

