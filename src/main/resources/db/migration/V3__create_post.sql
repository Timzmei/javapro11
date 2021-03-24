create table post (id  bigserial not null, is_blocked boolean not null, post_text text, time int8 not null, title varchar(255), author_id int8 not null, primary key (id));
alter table if exists post add constraint FKbafujapncubfpx85cmv9wrtwg foreign key (author_id) references person;

