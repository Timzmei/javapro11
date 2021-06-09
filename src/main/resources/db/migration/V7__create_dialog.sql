
CREATE TABLE dialog (
	id bigserial not null,
    owner_id int8 not null,
    is_deleted boolean,
    invite_code varchar(255),
    primary key (id));

    ALTER SEQUENCE dialog_id_seq START 10 INCREMENT 1;
