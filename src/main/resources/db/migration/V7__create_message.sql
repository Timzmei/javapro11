CREATE TYPE rd_status AS ENUM ('SENT', 'READ');

CREATE TABLE message (
	id bigserial not null,
    time timestamp not null,
    author_id bigserial not null,
    recipient_id bigserial not null,
    message_text text,
	read_status rd_status not null,
    primary key (id));