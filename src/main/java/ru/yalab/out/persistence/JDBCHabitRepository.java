package ru.yalab.out.persistence;

import ru.yalab.entity.Habit;
import ru.yalab.out.database.DataBaseTemplate;
import ru.yalab.repository.HabitRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCHabitRepository implements HabitRepository {
    DataBaseTemplate dataBaseTemplate;

    public JDBCHabitRepository(DataBaseTemplate dataBaseTemplate) {
        this.dataBaseTemplate = dataBaseTemplate;
    }

    @Override
    public void addHabit(Habit habit) throws SQLException {
        String sql = "INSERT INTO table_schema.habits(title, description, frequency, user_email) VALUES (?, ?, ?, ?)";

        try(Connection connection = dataBaseTemplate.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, habit.getTitle());
                    statement.setString(2, habit.getDescription());
                    statement.setString(3, habit.getFrequency());
                    statement.setString(4, habit.getUserEmail());
                    statement.execute();
            System.out.println("Привычка добавлена: " + habit.getUserEmail());
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    @Override
    public void deleteHabit(long id) throws SQLException{
        String sql = "DELETE FROM table_schema.habits WHERE id = ?";
        try(Connection connection = dataBaseTemplate.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            System.out.println("Habit DELETE: " + id);
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    @Override
    public Habit findById(Long id) throws SQLException {
        String sql = "SELECT * FROM table_schema.habits WHERE id = ?";
        try (Connection connection = dataBaseTemplate.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Habit(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("frequency"),
                        rs.getString("user_email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteById(long id) throws SQLException {
        String sql = "DELETE FROM table_schema.habits WHERE id = ?";
        try(Connection connection = dataBaseTemplate.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public List<Habit> findAllByUserEmail(String userMail) throws SQLException {
        List<Habit> habits = new ArrayList<>();
        String sql = "SELECT * FROM table_schema.habits WHERE user_email = ?";

        try(Connection connection = dataBaseTemplate.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userMail);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    long id = rs.getLong("id");
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    String frequency = rs.getString("frequency");
                    Habit habit = new Habit(id, title, description, frequency, userMail);
                    habits.add(habit);
                }
            }
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return habits;
    }

    @Override
    public void findAll() throws SQLException {

        String sql = "SELECT * FROM table_schema.habits";

        try(Connection connection = dataBaseTemplate.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                long id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String frequency = resultSet.getString("frequency");
                String userEmail = resultSet.getString("user_email");
                Habit habit = new Habit(id, title, description, frequency, userEmail);

                System.out.println(habit);
            }
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    @Override
    public void updateHabit(Habit habit) throws SQLException {

        String sql = "UPDATE table_schema.habits SET title = ?, " +
                "description = ?, " +
                "frequency = ?, " +
                "user_email = ? " +
                "WHERE id = ?";

        try (Connection connection = dataBaseTemplate.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, habit.getTitle());
            statement.setString(2, habit.getDescription());
            statement.setString(3, habit.getFrequency());
            statement.setString(4, habit.getUserEmail());
            statement.setLong(5, habit.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                System.out.println("Привычка с id " + habit.getId() + " не найдена для обновления.");
            } else {
                System.out.println("Привычка успешно обновлена.");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении привычки: " + e.getMessage());
            throw e;
        }
    }
}
