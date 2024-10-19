package ru.yalab.uselogic;

import ru.yalab.repository.HabitRepository;

import java.sql.SQLException;

/**
 * Класс UpdateHabit отвечает за обновление информации о привычках пользователя.
 *
 * Поля:
 * - habitRepo: репозиторий привычек, используемый для доступа к данным о привычках.
 *
 * Методы:
 * - execute(String userEmail, String oldTitle, String newTitle, String newDescription, String newFrequency):
 * Обновляет привычку пользователя с указанным старым заголовком новыми значениями заголовка, описания и частоты.
 *
 * @param userEmail адрес электронной почты пользователя, чью привычку нужно обновить.
 * @param oldTitle старый заголовок привычки, которую нужно обновить.
 * @param newTitle новый заголовок для привычки.
 * @param newDescription новое описание привычки.
 * @param newFrequency новая частота выполнения привычки.
 *
 * @return true, если обновление было успешным; false, если привычка не найдена или обновление не удалось.
 */

public class UpdateHabit {
    private final HabitRepository habitRepo;

    public UpdateHabit(HabitRepository habitRepo) {
        this.habitRepo = habitRepo;
    }

    public boolean execute(String userEmail,
                           String oldTitle,
                           String newTitle,
                           String newDescription,
                           String newFrequency) throws SQLException {
        return habitRepo.updateHabit(userEmail, oldTitle, newTitle, newDescription, newFrequency);
    }
}
