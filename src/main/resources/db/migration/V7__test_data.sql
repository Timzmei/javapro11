INSERT INTO person
    (first_name, reg_date, password, is_approved, messages_permission, last_online_time, is_blocked, email)
    VALUES
    ('Petr', current_timestamp, '$2y$12$mWD0Uz3iQN9VKoU0TjkDteXm.ngUgEz13AiUqMGKeQ6J1Th24K.uq', TRUE, 'ALL', current_timestamp, FALSE, 'mail@mail.ru'),
    ('Anny', current_timestamp, '$2y$12$mWD0Uz3iQN9VKoU0TjkDteXm.ngUgEz13AiUqMGKeQ6J1Th24K.uq', TRUE, 'ALL', current_timestamp, FALSE, 'my@mail.ru');