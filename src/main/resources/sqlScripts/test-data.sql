insert into users (name, password, enabled)
    values ('admin', 'admin', TRUE);
insert into users (name, password, enabled)
    values ('user', 'user', TRUE);

insert into user_roles (user_id, roles)
    values (1, 'ROLE_ADMIN');
insert into user_roles (user_id, roles)
    values (2, 'ROLE_USER');

insert into dishes (name, price)
    values ('fish', 2.33);
insert into dishes (name, price)
    values ('beer', 1.00);
insert into dishes (name, price)
    values ('pizza', 4.99);
insert into dishes (name, price)
    values ('fresh meat', 666.66);

insert into lunches (NAME, date, restaurant_id)
    values ('FISH WITH BEER', '2019-12-30', 1);
insert into lunches (NAME, date, restaurant_id)
    values ('PIZZA WITH MEAT', '2019-12-30', 2);

insert into restaurants (name, address)
    values ('Primary restaurant', 'Lenina, 3');
insert into restaurants (name, address)
    values ('Secondary restaurant', 'Azina, 666');

insert into votes (user_id, restaurant_id, date_time)
    values (1, 2, '2019-12-30 21:21:21');
insert into votes (user_id, restaurant_id, date_time)
    values (2, 1, '2019-12-30 10:11:11');
insert into votes (user_id, restaurant_id, date_time)
    values (1, 1, '2020-01-06 10:11:11');
insert into votes (user_id, restaurant_id, date_time)
    values (2, 2, '2020-01-06 23:59:59');

insert into lunches_dishes (lunch_id, dish_id)
    values (1, 1);
insert into lunches_dishes (lunch_id, dish_id)
    values (1, 2);
insert into lunches_dishes (lunch_id, dish_id)
    values (2, 3);
insert into lunches_dishes (lunch_id, dish_id)
    values (2, 4);

-- insert into restaurants_lunches (restaurant_id, lunch_id)
--     values (1, 2);
-- insert into restaurants_lunches (restaurant_id, lunch_id)
--     values (2, 1);

