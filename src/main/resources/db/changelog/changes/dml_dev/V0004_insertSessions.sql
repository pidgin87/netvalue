insert into charging_sessions(created_date, last_modified_date, version,
                              start_time, start_meter, end_time,
                              end_meter, charge_connector_id, rfid_tag_id,
                              vehicle_id)
values (now(), now(), 1, now(), 1, now(), 10, 1, 1, 1);

insert into charging_sessions(created_date, last_modified_date, version,
                              start_time, start_meter, end_time,
                              end_meter, charge_connector_id, rfid_tag_id,
                              vehicle_id)
values (now(), now(), 1, now(), 2, now(), 51, 1, 1, 1);

insert into charging_sessions(created_date, last_modified_date, version,
                              start_time, start_meter, end_time,
                              end_meter, charge_connector_id, rfid_tag_id,
                              vehicle_id)
values (now(), now(), 1, now(), 5, now(), 190, 1, 1, 1);