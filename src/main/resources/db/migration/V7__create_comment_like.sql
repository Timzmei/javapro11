create table comment_like(
    id bigserial not null,
    time timestamp not null,
    person_id bigserial not null,
    comment_id bigserial not null,
    primary key (id)
)