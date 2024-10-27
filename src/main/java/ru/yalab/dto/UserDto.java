package ru.yalab.dto;

import ru.yalab.entity.UserRole;
import ru.yalab.entity.UserStatus;

/**
 * класс UserDTO обьект передачи данных для пользователя
 * Поля: id, name, email, role и status пользователя
 */
public class UserDto {
    private long id;
    private String name;
    private String email;
    private UserRole role;
    private UserStatus status;

    // Конструктор без параметров (для сериализации)
    public UserDto() {}

    // Конструктор для инициализации всех полей
    public UserDto(long id, String name, String email, UserRole role, UserStatus status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}
