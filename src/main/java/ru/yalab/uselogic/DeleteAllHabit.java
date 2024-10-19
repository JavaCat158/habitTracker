package ru.yalab.uselogic;

import ru.yalab.repository.HabitRepository;

import java.sql.SQLException;

public class DeleteAllHabit {
    private HabitRepository habitRepo;

    public DeleteAllHabit(HabitRepository habitRepo) {
        this.habitRepo = habitRepo;
    }

    public void deleteAllHabits(String userMail) throws SQLException {
        habitRepo.deleteAll(userMail);
    }
}
