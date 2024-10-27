package ru.yalab.out.database;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LiquibaseLoader {
    private static final String URL = "jdbc:postgresql://localhost:5432/yalab_db";
    private static final String USER = "yalab";
    private static final String PASSWORD = "password123";
    private static final String CREATE_TABLE_CHANGELOG = "db/changelog/databaseChangeLog.yaml";


    public void start() throws LiquibaseException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));

            Liquibase liquibaseCreateTable = new Liquibase(CREATE_TABLE_CHANGELOG, new ClassLoaderResourceAccessor(), database);
            liquibaseCreateTable.update();
            System.out.println("Создание таблиц успешно завершено!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}