CREATE TABLE users
(
    ID       INT          NOT NULL AUTO_INCREMENT,
    NAME     VARCHAR(60)  NOT NULL,
    PASSWORD VARCHAR(255) NOT NULL,
    ENABLED  BOOLEAN      NOT NULL,
    PRIMARY KEY (ID),
    constraint users_unique_name_idx unique (NAME)
);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    roles   VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, roles),
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE dishes
(
    ID    INT          NOT NULL AUTO_INCREMENT,
    NAME  VARCHAR(255) NOT NULL,
    PRICE NUMBER       NOT NULL,
    PRIMARY KEY (ID),
    constraint dishes_unique_name_idx unique (NAME)
);

CREATE TABLE lunches
(
    ID            INT  NOT NULL AUTO_INCREMENT,
    NAME          VARCHAR(255),
    DATE          DATE NOT NULL,
    RESTAURANT_ID INT,
    PRIMARY KEY (ID),
    constraint lunches_unique_restaurant_date unique (DATE, RESTAURANT_ID)
);

CREATE TABLE restaurants
(
    ID      INT NOT NULL AUTO_INCREMENT,
    NAME    VARCHAR(255),
    ADDRESS VARCHAR(255),
    PRIMARY KEY (ID),
    constraint restaurants_unique_restaurant_name unique (NAME),
    constraint restaurants_unique_restaurant_address unique (ADDRESS)
);

CREATE TABLE votes
(
    ID            INT       NOT NULL AUTO_INCREMENT,
    USER_ID       INT       NOT NULL,
    RESTAURANT_ID INT       NOT NULL,
    DATE_TIME     TIMESTAMP NOT NULL,

    CONSTRAINT user_idx UNIQUE (USER_ID, DATE_TIME),
    PRIMARY KEY (ID),
    FOREIGN KEY (USER_ID) REFERENCES users (ID) ON DELETE CASCADE,
    FOREIGN KEY (RESTAURANT_ID) REFERENCES restaurants (ID)
);

CREATE TABLE lunches_dishes
(
    LUNCH_ID INT NOT NULL,
    DISH_ID  INT NOT NULL,
    PRIMARY KEY (LUNCH_ID, DISH_ID),
    FOREIGN KEY (LUNCH_ID) REFERENCES lunches (ID),
    FOREIGN KEY (DISH_ID) REFERENCES dishes (ID)
);