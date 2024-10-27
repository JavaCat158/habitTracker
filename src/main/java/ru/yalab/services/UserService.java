package ru.yalab.services;

import ru.yalab.dto.LoginRequest;
import ru.yalab.dto.LoginResponse;
import ru.yalab.dto.UserDto;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

    UserDto blockUser(String email) throws SQLException;

    UserDto unblockUser(String email) throws SQLException;

    UserDto deleteUser(String email) throws SQLException;

    List<UserDto> getAllUsers() throws SQLException;

    LoginResponse login(LoginRequest loginRequest) throws SQLException;
}
