package org.example.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс {@code Habit} представляет привычку, которую пользователь отслеживает.
 * Включает информацию о названии, описании, частоте выполнения, дате создания,
 * email пользователя и датах выполнения привычки.
 */
public class Habit {
    private String title;
    private String description;
    private String frequency;
    private LocalDate creationDate;
    private String userEmail;
    private List<LocalDate> completedDates;

    /**
     * Конструктор для создания объекта {@code Habit} с указанными параметрами.
     * Дата создания автоматически устанавливается на текущую дату.
     *
     * @param title       название привычки
     * @param description описание привычки
     * @param frequency   частота выполнения привычки (например, "ежедневно", "еженедельно")
     * @param userEmail   email пользователя, создавшего привычку
     */
    public Habit(String title, String description, String frequency, String userEmail) {
        this.title = title;
        this.description = description;
        this.frequency = frequency;
        this.creationDate = LocalDate.now();
        this.userEmail = userEmail;
        this.completedDates = new ArrayList<>();
    }

    /**
     * Возвращает название привычки.
     *
     * @return название привычки
     */
    public String getTitle() {
        return title;
    }

    /**
     * Устанавливает новое название привычки.
     *
     * @param title новое название привычки
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Возвращает описание привычки.
     *
     * @return описание привычки
     */
    public String getDescription() {
        return description;
    }

    /**
     * Устанавливает новое описание привычки.
     *
     * @param description новое описание привычки
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Возвращает частоту выполнения привычки.
     *
     * @return частота выполнения привычки
     */
    public String getFrequency() {
        return frequency;
    }

    /**
     * Устанавливает новую частоту выполнения привычки.
     *
     * @param frequency новая частота выполнения привычки
     */
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    /**
     * Возвращает дату создания привычки.
     *
     * @return дата создания привычки
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Устанавливает новую дату создания привычки.
     *
     * @param creationDate новая дата создания привычки
     */
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Возвращает email пользователя, связанного с привычкой.
     *
     * @return email пользователя
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * Устанавливает email пользователя для привычки.
     *
     * @param userEmail новый email пользователя
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * Добавляет дату выполнения привычки.
     *
     * @param completedDate дата выполнения привычки
     */
    public void addCompletedDate(LocalDate completedDate) {
        completedDates.add(completedDate);
    }

    /**
     * Возвращает список дат, когда привычка была выполнена.
     *
     * @return список дат выполнения привычки
     */
    public List<LocalDate> getCompletedDates() {
        return completedDates;
    }

    /**
     * Рассчитывает текущий "streak" (цепочку последовательных дней, когда привычка была выполнена).
     *
     * @return длина текущей цепочки выполнения привычки
     */
    public int calculatedStreak() {
        int streak = 0;
        LocalDate today = LocalDate.now();

        for (int i = completedDates.size() - 1; i >= 0; i--) {
            LocalDate date = completedDates.get(i);
            if (date.equals(today.minusDays(streak))) {
                streak++;
            } else {
                break;
            }
        }
        return streak;
    }

    /**
     * Отмечает выполнение привычки на указанную дату.
     *
     * @param date дата, на которую нужно отметить выполнение
     */
    public void markCompleted(LocalDate date) {
        if (!completedDates.contains(date)) {
            completedDates.add(date);
        }
    }

    /**
     * Возвращает строковое представление привычки, включая название, описание, частоту и дату создания.
     *
     * @return строковое представление привычки
     */
    @Override
    public String toString() {
        return "Привычка " + userEmail +": " + '\n' +
                "\tназвание= " + title + '\n' +
                "\tописание=" + description + '\n' +
                "\tчастота выполнения= " + frequency + '\n' +
                "\tдата создания=" + creationDate + "\n";
    }
}
