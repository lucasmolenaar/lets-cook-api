-- Hashed password is 'password'
INSERT INTO users (username, password, enabled, email) VALUES ('user', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', TRUE, 'test@test.com');
INSERT INTO users (username, password, enabled, email) VALUES ('superuser', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', TRUE,'test@test.com');
INSERT INTO users (username, password, enabled, email) VALUES ('admin', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', TRUE, 'test@test.com');

INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority) VALUES ('superuser', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('superuser', 'ROLE_ADMIN');

INSERT INTO recipes (calories, name, time_in_minutes) VALUES (950, 'Zelfgemaakte Pizza', 30);
INSERT INTO ingredients (name, weight_in_grams, recipe_id) VALUES ('Deeg', 300, 1);
INSERT INTO ingredients (name, weight_in_grams, recipe_id) VALUES ('Tomatensaus', 40, 1);
INSERT INTO ingredients (name, weight_in_grams, recipe_id) VALUES ('Geraspte kaas', 50, 1);
INSERT INTO ingredients (name, weight_in_grams, recipe_id) VALUES ('Halve uienringen', 20, 1);
INSERT INTO ingredients (name, weight_in_grams, recipe_id) VALUES ('Tonijn', 100, 1);
INSERT INTO steps (step_count, description, recipe_id) VALUES (1, 'Maak het deeg plat', 1);
INSERT INTO steps (step_count, description, recipe_id) VALUES (2, 'Besmeer en versier de pizza', 1);
INSERT INTO steps (step_count, description, recipe_id) VALUES (3, 'Doe de pizza voor 15 minuten in de oven', 1);
INSERT INTO equipment (name, recipe_id) VALUES ('Deegroller', 1);
INSERT INTO equipment (name, recipe_id) VALUES ('Oven', 1);
INSERT INTO equipment (name, recipe_id) VALUES ('Pizzasnijder', 1);


INSERT INTO recipes (calories, name, time_in_minutes) VALUES (600, 'Vegetarische Curry', 45);
INSERT INTO ingredients (name, weight_in_grams, recipe_id) VALUES ('Basmati rijst', 70, 2);
INSERT INTO ingredients (name, weight_in_grams, recipe_id) VALUES ('Courgette', 20, 2);
INSERT INTO ingredients (name, weight_in_grams, recipe_id) VALUES ('Paprika', 20, 2);
INSERT INTO ingredients (name, weight_in_grams, recipe_id) VALUES ('Ui', 20, 2);
INSERT INTO ingredients (name, weight_in_grams, recipe_id) VALUES ('Kokosmelk', 100, 2);
INSERT INTO ingredients (name, weight_in_grams, recipe_id) VALUES ('Currypasta', 50, 2);
INSERT INTO ingredients (name, weight_in_grams, recipe_id) VALUES ('Tofu blokjes', 150, 2);
INSERT INTO steps (step_count, description, recipe_id) VALUES (1, 'Snij alle groenten in stukjes', 2);
INSERT INTO steps (step_count, description, recipe_id) VALUES (2, 'Kook de rijst voor 8 minuten', 2);
INSERT INTO steps (step_count, description, recipe_id) VALUES (3, 'Fruit de ui voor 2 minuten', 2);
INSERT INTO steps (step_count, description, recipe_id) VALUES (4, 'Voeg de tofu er bij en bak nog 2 minuten', 2);
INSERT INTO steps (step_count, description, recipe_id) VALUES (5, 'Doe de rest van de groenten bij de ui en bak voor 10 minuten op hoog vuur', 2);
INSERT INTO steps (step_count, description, recipe_id) VALUES (6, 'Voeg de currypasta toe en laat intrekken voor 3 minuten op middelhoog vuur', 2);
INSERT INTO steps (step_count, description, recipe_id) VALUES (7, 'Voeg de kokosmelk toe en laat 15 minuten op laag vuur koken', 2);
INSERT INTO steps (step_count, description, recipe_id) VALUES (8, 'Voeg de rijst toe aan de groente', 2);
INSERT INTO equipment (name, recipe_id) VALUES ('Wokpan', 1);
INSERT INTO equipment (name, recipe_id) VALUES ('Kookpan', 1);