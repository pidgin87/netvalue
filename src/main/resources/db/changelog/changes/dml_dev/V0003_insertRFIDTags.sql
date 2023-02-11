-- insert RFID tags
insert into rfid_tags(created_date, last_modified_date, version, tag_name, tag_number, customer_id)
values (now(), now(), 1, 'tag1', '9382134b-46f1-437f-b581-49c533a49661',
        (select id from users where username = 'customer'));

insert into rfid_tags(created_date, last_modified_date, version, tag_name, tag_number, customer_id)
values (now(), now(), 1, 'tag2', '8d88c18d-18b4-464c-ace4-f69c8e7af3ab',
        (select id from users where username = 'customer'));

insert into rfid_tags(created_date, last_modified_date, version, tag_name, tag_number, customer_id)
values (now(), now(), 1, 'tag3', '86a06f1e-1009-487d-ad31-bee7553c58de',
        (select id from users where username = 'customer2'));