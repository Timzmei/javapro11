create table post_like (
    id  bigserial not null,
    time timestamp not null,
    person_id int8 not null,
    post_id int8 not null,
    primary key (id));