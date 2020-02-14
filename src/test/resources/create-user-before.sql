delete from user_role;
delete from usr;

insert into usr(id, active, password, username) values
(1, true, '$2a$08$bNyv6Txp3CwKtupA.UvrTeEnt/ZCPgG7zO5stQmwjjS9akM1uZcz6', 'denis'),
(2, true, '$2a$08$bNyv6Txp3CwKtupA.UvrTeEnt/ZCPgG7zO5stQmwjjS9akM1uZcz6', 'test-user');

insert into user_role(user_id, roles) values
(1, 'USER'), (1, 'ADMIN'),
(2, 'USER');