package org.example;

import org.example.controllers.NotificationController;
import org.example.in.web.AppInitializer;
import org.example.in.cli.HabitTrackerCli;

public class Main {
    public static void main(String[] args) {
        AppInitializer appInitializer = new AppInitializer();
        NotificationController notificationController = appInitializer.init();
        HabitTrackerCli cli = new HabitTrackerCli();
        notificationController.sendNotification("test@test.ru"); // опционально
        cli.start();
    }
}