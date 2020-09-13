create table if not exists post_comments (
    id int8 not null, text varchar(1000) not null, author int8, post int8, primary key (id)
);