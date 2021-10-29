INSERT INTO role (name) VALUES ('user');

INSERT INTO user (id, first_name, last_name, address, postal_code, username, password, enabled) VALUES(
                                                                                                          '1', 'John', 'Doe', '123 Yonge St',
                                                                                                          '123 456', 'admin',
                                                                                                          '$2a$10$bN7OWEvi6rTqJEYbZfDOg.FHmG.xPTDxJR1k9LzsR4O6Nt8zuIKwq',
                                                                                                          '1');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);