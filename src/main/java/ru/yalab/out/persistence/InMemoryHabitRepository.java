package ru.yalab.out.persistence;

import ru.yalab.entity.Habit;
import ru.yalab.out.database.DataBaseTemplate;
import ru.yalab.repository.HabitRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс InMemoryHabitRepository реализует интерфейс HabitRepository и предоставляет методы
 * для управления привычками, которые хранятся в памяти.
 * <p>
 * Методы включают:
 * - Сохранение новой привычки.
 * - Поиск привычки по названию и email пользователя.
 * - Удаление привычки по названию и email пользователя.
 * - Поиск всех привычек пользователя по его email.
 * - Вывод всех сохраненных привычек в консоль.
 * - Обновление привычки по параметрам: email пользователя, старое название, новое название, описание и частота выполнения.
 */

public class InMemoryHabitRepository implements HabitRepository {
    private final DataBaseTemplate dataBaseTemplate = new DataBaseTemplate();

    @Override
    public void save(Habit habit) {
        String sql = "INSERT INTO table_schema.habits (id, title, description, frequency, creation_date, user_email, completed_dates) " +
                "VALUES (nextval('sequence_schema.habit_id_seq'), ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataBaseTemplate.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, habit.getUserEmail());
            preparedStatement.setString(2, habit.getTitle());
            preparedStatement.setString(3, habit.getDescription());
            preparedStatement.setDate(4, Date.valueOf(habit.getCreationDate()));
            preparedStatement.setString(5, habit.getUserEmail());
            preparedStatement.setDate(6, Date.valueOf(habit.getCreationDate()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll(String userMail) {
        String sql = "DELETE FROM table_schema.habits WHERE user_email = ?";
        try (Connection connection = dataBaseTemplate.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userMail);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Habit findByTitle(String userEmail, String title) {
        String sql = "SELECT * FROM table_schema.habits WHERE user_email = ? AND title = ?";
        try (Connection connection = dataBaseTemplate.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userEmail);
            preparedStatement.setString(2, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Habit(
                        resultSet.getString("user_email"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("frequency")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteByTitle(String userMail, String title) {
        String sql = "DELETE FROM table_schema.habits WHERE user_email = ? AND title = ?";
        try (Connection connection = dataBaseTemplate.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userMail);
            preparedStatement.setString(2, title);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Habit> findAllByUserEmail(String email) {
        List<Habit> habits = new ArrayList<>();
        String sql = "SELECT * FROM table_schema.habits WHERE user_email = ?";
        try (Connection connection = dataBaseTemplate.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                habits.add(new Habit(
                        resultSet.getString("user_email"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("frequency")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return habits;
    }

    @Override
    public void findAll() {
        String sql = "SELECT * FROM table_schema.habits";
        try (Connection connection = dataBaseTemplate.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                System.out.println(new Habit(
                        resultSet.getString("user_email"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("frequency")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean updateHabit(String userEmail, String oldTitle, String newTitle, String newDescription, String newFrequency) {
        String sql = "UPDATE table_schema.habits SET title = ?, description = ?, frequency = ? WHERE user_email = ? AND title = ?";
        try (Connection connection = dataBaseTemplate.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newTitle);
            preparedStatement.setString(2, newDescription);
            preparedStatement.setString(3, newFrequency);
            preparedStatement.setString(4, userEmail);
            preparedStatement.setString(5, oldTitle);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}