insert into users (name, password, enabled)
    values
    ('admin', '$2y$12$jKb.p81Nt4f1usK1i2x6seCGsWJ8gX.ddN1D8MWG3ZczuxI.rbiM.', TRUE),
    ('user', '$2y$12$RcRYQROajLB3kXau8IEMfuv90BSwKb.btC6RZgAEh0eY8.e7k2ocK', TRUE);

insert into user_roles (user_id, roles)
    values
    (1, 'ROLE_ADMIN'),
    (2, 'ROLE_USER');

insert into dishes (name, price)
    values
     ('fish', 2.33),
     ('beer', 1.00),
     ('pizza', 4.99),
     ('fresh meat', 666.66);

insert into lunches (NAME, date, restaurant_id)
    values
     ('FISH WITH BEER', current_date , 1),
     ('PIZZA WITH MEAT', '2020-01-01' , 2);

insert into restaurants (name, address)
    values
     ('Primary restaurant', 'Lenina, 3'),
     ('Secondary restaurant', 'Azina, 666');

insert into votes (user_id, restaurant_id, date_time)
    values
     (1, 2, '2019-12-30 21:21:21'),
     (2, 1, '2019-12-30 10:11:11'),
     (1, 1, '2020-01-06 10:11:11'),
     (2, 2, '2020-01-06 23:59:59');

insert into lunches_dishes (lunch_id, dish_id)
    values
     (1, 1),
     (1, 2),
     (2, 3),
     (2, 4);

