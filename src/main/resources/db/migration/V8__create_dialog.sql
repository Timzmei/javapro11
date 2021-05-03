CREATE TABLE dialog (
	id bigserial not null,
    owner_id bigserial not null,
    is_deleted boolean,
    invite_code varchar(255),
    primary key (id));