-- Создание схем, если они не существуют
CREATE SCHEMA IF NOT EXISTS sequence_schema;
CREATE SCHEMA IF NOT EXISTS table_schema;

-- Создание последовательностей для генерации идентификаторов
CREATE SEQUENCE IF NOT EXISTS sequence_schema.user_id_seq
START WITH 1
INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS sequence_schema.habit_id_seq
START WITH 1
INCREMENT BY 1;

-- Создание таблицы пользователей
CREATE TABLE IF NOT EXISTS table_schema.users (
                                                  id BIGINT PRIMARY KEY DEFAULT nextval('sequence_schema.user_id_seq'),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL
    );

-- Создание таблицы привычек
CREATE TABLE IF NOT EXISTS table_schema.habits (
                                                   id BIGINT PRIMARY KEY DEFAULT nextval('sequence_schema.habit_id_seq'),
    title VARCHAR(255) NOT NULL,
    description TEXT,
    frequency VARCHAR(100) NOT NULL,
    creation_date DATE NOT NULL DEFAULT CURRENT_DATE,
    user_email VARCHAR(255) NOT NULL,
    completed_dates JSONB,  -- Используем JSONB для хранения массива дат
    FOREIGN KEY (user_email) REFERENCES table_schema.users(email)  -- Внешний ключ для связи с пользователями
    );
