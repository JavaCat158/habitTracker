package ru.yalab;

import ru.yalab.controllers.NotificationController;
import ru.yalab.in.web.AppInitializer;
import ru.yalab.in.cli.HabitTrackerCli;
import ru.yalab.out.database.LiquibaseLoader;

public class Main {
    public static void main(String[] args) throws Exception {
        LiquibaseLoader liquibaseLoader = new LiquibaseLoader();
        liquibaseLoader.start();
        AppInitializer appInitializer = new AppInitializer();
        NotificationController notificationController = appInitializer.init();
        HabitTrackerCli cli = new HabitTrackerCli();
        notificationController.sendNotification("test@test.ru"); // опционально
        cli.start();
    }
}