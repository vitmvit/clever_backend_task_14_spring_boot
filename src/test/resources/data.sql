INSERT INTO house (id, uuid, area, city, country, street, number, create_date)
VALUES (201, '6d316b83-126e-4090-bc81-4125a98923c0', 'AreaOne', 'City', 'Country', 'StreetOne', 19,
        '2024-01-19 13:16:13.992677');

insert into person (create_date, house_id, name, passport_number, passport_series, sex, surname, update_date, uuid, id)
values ('2024-01-19 13:23:18.356477', 201, 'Sophia', '1234567', 'AB', 'MALE', 'Johnson', '2024-01-19 13:23:18.356477',
        '33447590-3f82-4ae7-a9f6-4b140475c18f', 1);


INSERT INTO house_history (id, house_id, person_id, date, type)
VALUES (1, 201, 1, '2024-01-19 15:52:48.646771', 'TENANT');

INSERT INTO house_owner (house_id, person_id)
VALUES (201, 1);