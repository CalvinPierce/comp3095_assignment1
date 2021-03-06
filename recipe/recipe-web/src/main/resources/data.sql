INSERT INTO role (name)
VALUES ('user');

INSERT INTO user (id, first_name, last_name, address, postal_code, username, password, enabled)
VALUES ('1', 'John', 'Doe', '123 Yonge St',
        '123 456', 'admin',
        '$2a$10$bN7OWEvi6rTqJEYbZfDOg.FHmG.xPTDxJR1k9LzsR4O6Nt8zuIKwq',
        '1');

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1);

INSERT INTO instructions (id, instructions)
VALUES (1, 'Step 1
In a large bowl, mix flour, sugar, baking powder and salt. Make a well in the center, and pour in milk, egg and oil. Mix until smooth.

Step 2
Heat a lightly oiled griddle or frying pan over medium high heat. Pour or scoop the batter onto the griddle, using approximately 1/4 cup for each pancake. Brown on both sides and serve hot.');

INSERT INTO RECIPE (id, name, cook_time, prep_time,total_time, ingredients, date_added, instructions_id)
VALUES (1, 'Pancakes', 5, 10, 15, '1 cup all-purpose flour,

2 tablespoons white sugar,

2 teaspoons baking powder,

1 teaspoon salt,

1 egg, beaten,

1 cup milk,

2 tablespoons vegetable oil', '2021-12-01', 1);


INSERT INTO users_recipes (user_id, recipe_id)
VALUES (1, 1);

INSERT INTO meal (id, date, name)
VALUES (1, '2021-11-07', 'Breakfast');

INSERT INTO meal_recipe (meal_id, recipe_id)
VALUES (1, 1);

INSERT INTO users_meals (meal_id, user_id)
VALUES (1, 1);


INSERT INTO event (id, name, event_date) VALUES
    ('1','Event 1','2021-12-08');

INSERT INTO user_event (event_id, user_id)
VALUES (1, 1);

INSERT INTO meal_event (event_id, meal_id)
VALUES (1, 1);