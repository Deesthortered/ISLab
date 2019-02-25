DROP TABLE IF EXISTS SystemUsers;

CREATE TABLE SystemUsers (
    user_login      char(30) PRIMARY KEY NOT NULL,
    user_password   char(30) NOT NULL,
    user_role       int not NULL
)

INSERT INTO SystemUsers (user_login, user_password, user_role) VALUES ("admin",  "admin", 0);
INSERT INTO SystemUsers (user_login, user_password, user_role) VALUES ("view",       "1", 1);
INSERT INTO SystemUsers (user_login, user_password, user_role) VALUES ("import",     "2", 2);
INSERT INTO SystemUsers (user_login, user_password, user_role) VALUES ("export",     "3", 3);

ALTER USER admin IDENTIFIED WITH mysql_native_password BY 'admin';