DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Item;
DROP TABLE IF EXISTS Role;
CREATE TABLE IF NOT EXISTS Item
(
    id      BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name    VARCHAR(80)           NOT NULL,
    status  VARCHAR(20)           NOT NULL,
    deleted BOOLEAN DEFAULT FALSE
);
CREATE TABLE IF NOT EXISTS Role
(
    id   BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name VARCHAR(30)           NOT NULL
);
CREATE TABLE IF NOT EXISTS User
(
    id       BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    username VARCHAR(70)           NOT NULL,
    password VARCHAR(60)           NOT NULL,
    role_id  BIGINT                NOT NULL,
    FOREIGN KEY (role_id) REFERENCES Role (id),
    UNIQUE (username)
);
INSERT INTO Role (name)
VALUES ('ADMINISTRATOR');
INSERT INTO Role (name)
VALUES ('CUSTOMER');
INSERT INTO User (username, password, role_id)
VALUES ('admin', '$2a$09$OJhhuE6MFbjADXQVFUm7d.3puJ0PXoic8P.PKgSK1cnpbUvvytSSW',
        (SELECT id FROM Role WHERE name = 'ADMINISTRATOR'));
INSERT INTO User (username, password, role_id)
VALUES ('user', '$2a$09$QujSO5HRvKghLdLJVspYP.hLj9jHYRY2GSuP7FkNT.PRjIK3HST1.',
        (SELECT id FROM Role WHERE name = 'CUSTOMER'));
INSERT INTO Item (name, status)
VALUES ('TestItem', 'READY');
INSERT INTO Item (name, status)
VALUES ('TestItem2', 'STEADY');
INSERT INTO Item (name, status)
VALUES ('TestItem3', 'STEADY');
INSERT INTO Item (name, status)
VALUES ('TestItem4', 'GO');
INSERT INTO Item (name, status)
VALUES ('TestItem5', 'READY');
INSERT INTO Item (name, status)
VALUES ('TestItem6', 'GO');
INSERT INTO Item (name, status)
VALUES ('TestItem7', 'STEADY');