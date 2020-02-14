delete from post;

insert into post(id, text,  status, author) values
(1,  'text of the first post', 'NEW', 1),
(2,  'text of the second post', 'NEW', 2),
(3,  'text of the third post', 'NEW', 2),
(4,  'text of the fourth post', 'NEW', 1);

alter sequence hibernate_sequence restart with 10;