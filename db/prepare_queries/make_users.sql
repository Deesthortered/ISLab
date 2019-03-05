DROP TABLE IF EXISTS SystemUsers;

CREATE TABLE SystemUsers (
    user_login      char(30) PRIMARY KEY NOT NULL,
    user_password   char(30) NOT NULL,
    user_role       int not NULL,
    user_lastvisit  date not NULL
);

INSERT INTO SystemUsers (user_login, user_password, user_role, user_lastvisit) VALUES ('admin',  'admin', 0, '2019-01-01');
INSERT INTO SystemUsers (user_login, user_password, user_role, user_lastvisit) VALUES ('view',       '1', 1, '2019-01-01');
INSERT INTO SystemUsers (user_login, user_password, user_role, user_lastvisit) VALUES ('import',     '2', 2, '2019-01-01');
INSERT INTO SystemUsers (user_login, user_password, user_role, user_lastvisit) VALUES ('export',     '3', 3, '2019-01-01');

ALTER USER admin IDENTIFIED WITH mysql_native_password BY 'admin';