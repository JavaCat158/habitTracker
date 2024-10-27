package ru.yalab.services;

import ru.yalab.dto.LoginRequest;
import ru.yalab.dto.LoginResponse;
import ru.yalab.dto.UserDto;
import ru.yalab.entity.User;
import ru.yalab.entity.UserStatus;
import ru.yalab.mapper.UserMapper;
import ru.yalab.repository.UserRepository;

import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto blockUser(String email) throws SQLException {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getStatus() != UserStatus.BLOCKED) {
            user.blockUser();
            userRepository.updateUserProfile(user); // Обновляем статус в базе
            return userMapper.userToUserDto(user);
        }
        return null;
    }

    @Override
    public UserDto unblockUser(String email) throws SQLException {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getStatus() == UserStatus.BLOCKED) {
            user.unblockUser();
            userRepository.updateUserProfile(user); // Обновляем статус в базе
            return userMapper.userToUserDto(user);
        }
        return null;
    }


    @Override
    public UserDto deleteUser(String email) throws SQLException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            userRepository.deleteByEmail(email);
            return userMapper.userToUserDto(user);
        }
        return null;
    }

    @Override
    public List<UserDto> getAllUsers() throws SQLException {
        LinkedHashSet<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws SQLException {
        User user = userRepository.findByEmail(loginRequest.getEmail());

        if (user == null) {
            return new LoginResponse("Ошибка", "Такого пользователя не существует или он был удален!");
        }

        if (user.getStatus() == UserStatus.BLOCKED) {
            return new LoginResponse("Ошибка", "Вы заблокированы, обратитесь в поддержку!");
        }

        if (user.getPassword().equals(loginRequest.getPassword())) {
            // Создаем объект UserDto для успешного входа
            UserDto userDto = userMapper.userToUserDto(user); // Используем маппер для преобразования
            return new LoginResponse("Успешно", "Вход выполнен.", userDto); // Возвращаем объект LoginResponse с информацией о пользователе
        } else {
            return new LoginResponse("Ошибка", "Неверный пароль");
        }
    }
}
