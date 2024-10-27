package ru.yalab.dto;

import java.time.LocalDate;
import java.util.List;

public class HabitDetailsDTO {
    private Long id;
    private String title;
    private String description;
    private String frequency;
    private LocalDate creationDate;
    private String userEmail;
    private List<LocalDate> completedDates;

    public HabitDetailsDTO() {
    }

    public HabitDetailsDTO(Long id,
                           String title,
                           String description,
                           String frequency,
                           LocalDate creationDate,
                           String userEmail,
                           List<LocalDate> completedDates) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.frequency = frequency;
        this.creationDate = creationDate;
        this.userEmail = userEmail;
        this.completedDates = completedDates;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<LocalDate> getCompletedDates() {
        return completedDates;
    }

    public void setCompletedDates(List<LocalDate> completedDates) {
        this.completedDates = completedDates;
    }
}
