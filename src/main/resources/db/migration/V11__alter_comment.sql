ALTER TABLE comment ADD is_deleted boolean default false;
ALTER TABLE comment ALTER COLUMN is_blocked SET DEFAULT false;

ALTER TABLE post ADD is_deleted boolean default false;
