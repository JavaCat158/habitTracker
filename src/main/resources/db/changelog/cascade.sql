ALTER TABLE table_schema.habits
DROP CONSTRAINT habits_user_email_fkey;

ALTER TABLE table_schema.habits
ADD CONSTRAINT fk_user_email
FOREIGN KEY (user_email)
REFERENCES table_schema.users(email)
ON DELETE CASCADE;