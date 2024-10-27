package ru.yalab.services;

import java.util.regex.Pattern;

/**
 * Класс EmailValidator предоставляет методы для валидации адресов электронной почты.
 */
public class EmailValidator {
    private static final String EMAIL_REGEX = "^([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * Проверяет, соответствует ли указанный адрес электронной почты заданному шаблону.
     *
     * @param email адрес электронной почты для проверки
     * @return true, если адрес электронной почты соответствует шаблону, иначе false
     */
    public static boolean isValidate(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
