insert into usr (id, active, password, username)
values (10, true, 'admin', 'admin');
insert into user_role (user_id, roles)
values (10, 'USER'), (10, 'ADMIN');