create table comment (
    id  bigserial not null,
    author_id int8,
    comment_text text,
    is_blocked boolean not null default false,
    parent_id int,
    time timestamp not null,
    post_id int8 not null,
    is_deleted boolean default false,
    primary key (id));