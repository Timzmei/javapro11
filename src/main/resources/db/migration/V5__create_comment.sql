create table comment (id  bigserial not null, author_id int8, comment_text text, is_blocked boolean not null, parent_id int, time int8 not null, post_id int8 not null, primary key (id));
alter table if exists comment add constraint FKs1slvnkuemjsq2kj4h3vhx7i1 foreign key (post_id) references post;


