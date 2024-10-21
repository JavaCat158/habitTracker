INSERT INTO table_schema.users (id, name, email, password, role, status)
VALUES
    (nextval('sequence_schema.user_id_seq'), 'user1', 'user1@test.ru', 'pass1', 'USER', 'ACTIVE'),
    (nextval('sequence_schema.user_id_seq'), 'user2', 'user2@test.ru', 'pass2', 'USER', 'ACTIVE'),
    (nextval('sequence_schema.user_id_seq'), 'user3', 'user3@test.ru', 'pass3', 'ADMIN', 'ACTIVE');


INSERT INTO table_schema.habits (id, title, description, frequency, creation_date, user_email, completed_dates)
VALUES
    (nextval('sequence_schema.habit_id_seq'), 'Пить воду', 'Пить 2 литра воды в день', 'ежедневно', CURRENT_DATE,
     'user1@test.ru', '{}'),
    (nextval('sequence_schema.habit_id_seq'), 'Чтение книг', 'Читать 20 страниц книги каждый день', 'ежедневно',
     CURRENT_DATE, 'user1@test.ru', '{}'),
    (nextval('sequence_schema.habit_id_seq'), 'Бег', 'Бегать по утрам', 'ежедневно', CURRENT_DATE, 'user1@test.ru',
     '{}'),
    (nextval('sequence_schema.habit_id_seq'), 'Изучение языков', 'Учить 10 новых слов в день', 'ежедневно',
     CURRENT_DATE, 'user2@test.ru', '{}');
