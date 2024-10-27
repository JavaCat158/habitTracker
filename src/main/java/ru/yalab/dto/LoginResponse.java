package ru.yalab.dto;

public class LoginResponse {
    private String status; // Успешно или Ошибка
    private String message; // Сообщение об ошибке или успехе
    private UserDto user; // Информация о пользователе, если вход успешен

    // Конструктор для успешного входа
    public LoginResponse(String status, String message, UserDto user) {
        this.status = status;
        this.message = message;
        this.user = user;
    }

    // Конструктор для ошибок
    public LoginResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    // Геттеры и сеттеры
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}