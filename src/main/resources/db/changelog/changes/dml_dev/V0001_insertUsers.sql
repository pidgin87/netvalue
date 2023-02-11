-- insert roles
insert into roles (created_date, last_modified_date, version, role_name)
values (now(), now(), 1, 'ROLE_ADMIN');
insert into roles (created_date, last_modified_date, version, role_name)
values (now(), now(), 1, 'ROLE_CUSTOMER');

-- insert customers
insert into users(created_date, last_modified_date, version, name, username, user_password, role_id)
values (now(), now(), 1, 'Customer name', 'customer',
        '{bcrypt}$2a$10$vNR7BPAf.rSuB7z3C.mfT.1vUMzf//zkNqY/4y3D2ZfEW8MoARLEK',
        (select id from roles where role_name = 'ROLE_CUSTOMER'));

insert into users(created_date, last_modified_date, version, name, username, user_password, role_id)
values (now(), now(), 1, 'Customer name', 'customer2',
        '{bcrypt}$2a$10$vNR7BPAf.rSuB7z3C.mfT.1vUMzf//zkNqY/4y3D2ZfEW8MoARLEK',
        (select id from roles where role_name = 'ROLE_CUSTOMER'));

-- insert admins
insert into users(created_date, last_modified_date, version, name, username, user_password, role_id)
values (now(), now(), 1, 'Admin names', 'admin',
        '{bcrypt}$2a$10$vNR7BPAf.rSuB7z3C.mfT.1vUMzf//zkNqY/4y3D2ZfEW8MoARLEK',
        (select id from roles where role_name = 'ROLE_ADMIN'));