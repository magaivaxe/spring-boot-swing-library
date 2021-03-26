DELETE FROM authorities;
DELETE FROM users;

-- encrypt passwords after encryption's implementation.
INSERT INTO users (username, password, enabled) VALUES ('admin', 'admin@123', true);
INSERT INTO users (username, password, enabled) VALUES ('employee', 'employee@123', true);
INSERT INTO users (username, password, enabled) VALUES ('user', 'user@123', true);

INSERT INTO authorities (username, authority) VALUES ('admin', 'ADMIN');
INSERT INTO authorities (username, authority) VALUES ('admin', 'USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'EMPLOYEE');
INSERT INTO authorities (username, authority) VALUES ('employee', 'USER');
INSERT INTO authorities (username, authority) VALUES ('employee', 'EMPLOYEE');
INSERT INTO authorities (username, authority) VALUES ('user', 'USER');