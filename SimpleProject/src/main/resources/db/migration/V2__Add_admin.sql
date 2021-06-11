insert into usr (id, username, password, active, email)
values (1, 'admin', '123', true, 's1mpleknight@mail.ru');

insert into usr_role (usr_id, roles)
values (1, 'USER'), (1, 'ADMIN');