package ru.yalab.entity;

/**
 * Класс {@code User} представляет пользователя системы.
 * Включает информацию о пользователе, такую как имя, email, пароль,
 * а также его роль и статус блокировки.
 */
public class User {
    private long id;
    private String name;
    private String email;
    private String password;
    private UserRole role;
    private UserStatus status;

    /**
     * Конструктор для создания объекта {@code User}.
     *
     * @param name     имя пользователя
     * @param email    email пользователя
     * @param password пароль пользователя
     * @param role     роль пользователя (администратор или пользователь)
     */
    public User(long id, String name, String email, String password, UserRole role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = UserStatus.ACTIVE; // По умолчанию пользователь активен
    }

    /**
     * Возвращает имя пользователя.
     *
     * @return имя пользователя
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает новое имя пользователя.
     *
     * @param name новое имя пользователя
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Возвращает email пользователя.
     *
     * @return email пользователя
     */
    public String getEmail() {
        return email;
    }

    /**
     * Устанавливает новый email пользователя.
     *
     * @param email новый email пользователя
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Возвращает пароль пользователя.
     *
     * @return пароль пользователя
     */
    public String getPassword() {
        return password;
    }

    /**
     * Устанавливает новый пароль пользователя.
     *
     * @param password новый пароль пользователя
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Возвращает роль пользователя.
     *
     * @return роль пользователя
     */
    public UserRole getRole() {
        return role;
    }

    /**
     * Устанавливает новую роль пользователя.
     *
     * @param role новая роль пользователя
     */
    public void setRole(UserRole role) {
        this.role = role;
    }

    /**
     * Возвращает статус блокировки пользователя.
     *
     * @return статус блокировки пользователя
     */
    public UserStatus getStatus() {
        return status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * Устанавливает статус блокировки для пользователя.
     *
     * @param status новый статус блокировки
     */
    public void setStatus(UserStatus status) {
        this.status = status;
    }

    /**
     * Блокирует пользователя.
     */
    public void blockUser() {
        this.status = UserStatus.BLOCKED;
    }

    /**
     * Разблокирует пользователя.
     */
    public void unblockUser() {
        this.status = UserStatus.ACTIVE;
    }

    /**
     * Возвращает строковое представление пользователя.
     *
     * @return строковое представление пользователя
     */
    @Override
    public String toString() {
        return "User{" + "id= " + id + '\'' +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", status=" + status +
                '}';
    }
}
