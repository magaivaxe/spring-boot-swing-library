DELETE FROM authorities;
DELETE FROM users;

-- encrypt passwords after encryption's implementation.
INSERT INTO users (username, password, enabled) VALUES ('admin', '$2a$10$5sWofAdVw9.x3f34zJjjCOeqm854.hzS88iWb0D.TwjtdjLH/OQk2', true); -- password = admin@123
INSERT INTO users (username, password, enabled) VALUES ('employee', '$2a$10$sokalp666w5RjlSR0KeEEeTdradjN/VTK9PePQnSrR21NDAxClxEm', true); -- password = employee@123
INSERT INTO users (username, password, enabled) VALUES ('user', '$2a$10$FVEWOArf0ePqqhEI.6w0wugMygNOQ11wPcs9VbDT2p81kee7KNjmy', true); -- password = user@123

INSERT INTO authorities (username, authority) VALUES ('admin', 'ADMIN');
INSERT INTO authorities (username, authority) VALUES ('admin', 'USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'EMPLOYEE');
INSERT INTO authorities (username, authority) VALUES ('employee', 'USER');
INSERT INTO authorities (username, authority) VALUES ('employee', 'EMPLOYEE');
INSERT INTO authorities (username, authority) VALUES ('user', 'USER');