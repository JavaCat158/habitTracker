package org.example.uselogic;

import org.example.repository.HabitRepository;

public class DeleteAllHabit {
    private HabitRepository habitRepo;

    public DeleteAllHabit(HabitRepository habitRepo) {
        this.habitRepo = habitRepo;
    }

    public void deleteAllHabits(String userMail) {
        habitRepo.deleteAll(userMail);
    }
}
