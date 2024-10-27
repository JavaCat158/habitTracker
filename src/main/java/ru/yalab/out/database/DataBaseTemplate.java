package ru.yalab.out.database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseTemplate {
    private String url;
    private String username;
    private String password;
    private String driver;

    public DataBaseTemplate() {
        loadProperties();
    }

    private void loadProperties() {
        Properties properties = new Properties();
        try (InputStream inputProper = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (inputProper == null) {
                throw new FileNotFoundException("Фаил: application.properties не найден");
            }
            properties.load(inputProper);
            this.url = properties.getProperty("db.url");
            this.username = properties.getProperty("db.username");
            this.password = properties.getProperty("db.password");
            this.driver = properties.getProperty("db.driver");

            // Загружаем драйвер
            Class.forName(driver);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
