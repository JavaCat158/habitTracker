package org.example.entity;

/**
 * Класс {@code User} представляет пользователя системы.
 * Включает информацию о пользователе, такую как имя, email, пароль,
 * а также его статус (администратор или нет, заблокирован или нет).
 */
public class User {
    private String name;
    private String email;
    private String password;
    private boolean isAdmin;
    private boolean isBloced;

    /**
     * Конструктор для создания объекта {@code User}.
     *
     * @param name     имя пользователя
     * @param email    email пользователя
     * @param password пароль пользователя
     * @param isAdmin  флаг, является ли пользователь администратором
     */
    public User(String name, String email, String password, boolean isAdmin) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
        this.isBloced = false;
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
     * Возвращает, является ли пользователь администратором.
     *
     * @return {@code true}, если пользователь администратор, иначе {@code false}
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * Устанавливает статус администратора для пользователя.
     *
     * @param admin новый статус администратора
     */
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    /**
     * Возвращает, заблокирован ли пользователь.
     *
     * @return {@code true}, если пользователь заблокирован, иначе {@code false}
     */
    public boolean isBloced() {
        return isBloced;
    }

    /**
     * Устанавливает статус блокировки для пользователя.
     *
     * @param bloced новый статус блокировки
     */
    public void setBloced(boolean bloced) {
        isBloced = bloced;
    }

    /**
     * Блокирует пользователя.
     */
    public void blockUser() {
        this.isBloced = true;
    }

    /**
     * Разблокирует пользователя.
     */
    public void unblockUser() {
        this.isBloced = false;
    }

    /**
     * Возвращает строковое представление пользователя.
     *
     * @return строковое представление пользователя
     */
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                " email='" + email + '\'' +
                " password='" + password + '\'' +
                '}';
    }
}
