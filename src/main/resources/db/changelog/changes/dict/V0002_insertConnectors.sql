-- insert charge connectors
insert into charge_connectors(created_date, last_modified_date, version, connector_number, charge_point_id)
values (now(), now(), 1, 1, (select id from charge_points where serial_number = 'number1'));

insert into charge_connectors(created_date, last_modified_date, version, connector_number, charge_point_id)
values (now(), now(), 1, 2, (select id from charge_points where serial_number = 'number1'));

insert into charge_connectors(created_date, last_modified_date, version, connector_number, charge_point_id)
values (now(), now(), 1, 1, (select id from charge_points where serial_number = 'number2'));

insert into charge_connectors(created_date, last_modified_date, version, connector_number, charge_point_id)
values (now(), now(), 1, 2, (select id from charge_points where serial_number = 'number2'));
