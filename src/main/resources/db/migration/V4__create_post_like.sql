create table post_like (id  bigserial not null, time int8 not null, person_id int8 not null, post_id int8 not null, primary key (id));
alter table if exists post_like add constraint FK3sw1w4okji2yfol7u009an0by foreign key (person_id) references person;
alter table if exists post_like add constraint FKj7iy0k7n3d0vkh8o7ibjna884 foreign key (post_id) references post;

