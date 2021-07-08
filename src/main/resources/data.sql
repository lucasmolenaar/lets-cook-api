INSERT INTO users (username, password, enabled, email) VALUES ('user', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', TRUE, 'test@test.com');
INSERT INTO users (username, password, enabled, email) VALUES ('superuser', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', TRUE,'test@test.com');
INSERT INTO users (username, password, enabled, email) VALUES ('lucas', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', TRUE, 'test@test.com');
INSERT INTO users (username, password, enabled, email) VALUES ('admin', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', TRUE, 'test@test.com');

-- Hashed password is 'password'

INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('superuser', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('superuser', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority) VALUES ('lucas', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');