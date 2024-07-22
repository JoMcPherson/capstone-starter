DROP TABLE IF EXISTS shopping_cart;
DROP TABLE IF EXISTS order_line_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS profiles;
DROP TABLE IF EXISTS users;


CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL,
                       hashed_password VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL
);

CREATE TABLE profiles (
                          user_id INT NOT NULL,
                          first_name VARCHAR(50) NOT NULL,
                          last_name VARCHAR(50) NOT NULL,
                          phone VARCHAR(20) NOT NULL,
                          email VARCHAR(200) NOT NULL,
                          address VARCHAR(200) NOT NULL,
                          city VARCHAR(50) NOT NULL,
                          state VARCHAR(50) NOT NULL,
                          zip VARCHAR(20) NOT NULL,
                          PRIMARY KEY (user_id),
                          FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE categories (
                            category_id SERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            description TEXT
);

CREATE TABLE products (
                          product_id SERIAL PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          price NUMERIC(10, 2) NOT NULL,
                          category_id INT NOT NULL,
                          description TEXT,
                          color VARCHAR(20),
                          image_url VARCHAR(200),
                          stock INT NOT NULL DEFAULT 0,
                          featured BOOLEAN NOT NULL DEFAULT FALSE,
                          FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

CREATE TABLE orders (
                        order_id SERIAL PRIMARY KEY,
                        user_id INT NOT NULL,
                        date TIMESTAMP NOT NULL,
                        address VARCHAR(100) NOT NULL,
                        city VARCHAR(50) NOT NULL,
                        state VARCHAR(50) NOT NULL,
                        zip VARCHAR(20) NOT NULL,
                        shipping_amount NUMERIC(10, 2) NOT NULL DEFAULT 0,
                        FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE order_line_items (
                                  order_line_item_id SERIAL PRIMARY KEY,
                                  order_id INT NOT NULL,
                                  product_id INT NOT NULL,
                                  sales_price NUMERIC(10, 2) NOT NULL,
                                  quantity INT NOT NULL,
                                  discount NUMERIC(10, 2) NOT NULL DEFAULT 0,
                                  FOREIGN KEY (order_id) REFERENCES orders(order_id),
                                  FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- new tables
CREATE TABLE shopping_cart (
                               user_id INT NOT NULL,
                               product_id INT NOT NULL,
                               quantity INT NOT NULL DEFAULT 1,
                               PRIMARY KEY (user_id, product_id),
                               FOREIGN KEY (user_id) REFERENCES users(user_id),
                               FOREIGN KEY (product_id) REFERENCES products(product_id)
);

/*  INSERT Users  */
INSERT INTO users (username, hashed_password, role)
VALUES ('user','$2a$10$NkufUPF3V8dEPSZeo1fzHe9ScBu.LOay9S3N32M84yuUM2OJYEJ/.','ROLE_USER'),
       ('admin','$2a$10$lfQi9jSfhZZhfS6/Kyzv3u3418IgnWXWDQDk7IbcwlCFPgxg9Iud2','ROLE_ADMIN');

/* INSERT Profiles */
INSERT INTO profiles (user_id, first_name, last_name, phone, email, address, city, state, zip)
VALUES (1, 'Joe', 'Joesephus', '800-555-1234', 'joejoesephus@email.com', '789 Oak Avenue', 'Dallas', 'TX', '75051'),
       (2, 'Adam', 'Admamson', '800-555-1212', 'aaadamson@email.com', '456 Elm Street','Dallas','TX','75052');

/* INSERT Categories */
INSERT INTO categories (name, description)
VALUES ('Electronics', 'Explore the latest gadgets and electronic devices.'),
       ('Fashion', 'Discover trendy clothing and accessories for men and women.'),
       ('Home & Kitchen', 'Find everything you need to decorate and equip your home.');

/* INSERT Products */
-- electronics
INSERT INTO products (name, price, category_id, description, image_url, stock, featured, color)
VALUES ('Smartphone', 499.99, 1, 'A powerful and feature-rich smartphone for all your communication needs.', 'smartphone.jpg', 50, FALSE, 'Black'),
       ('Laptop', 899.99, 1, 'A high-performance laptop for work and entertainment.', 'laptop.jpg', 30, FALSE, 'Gray'),
       ('Headphones', 99.99, 1, 'Immerse yourself in music with these high-quality headphones.', 'headphones.jpg', 100, TRUE, 'White'),
       ('Men''s T-Shirt', 29.99, 2, 'A comfortable and stylish t-shirt for everyday wear.', 'mens-tshirt.jpg', 50, TRUE, 'Charcoal'),
       ('Men''s Jeans', 59.99, 2, 'Classic denim jeans for a timeless and casual look.', 'mens-jeans.jpg', 30, FALSE, 'Blue'),
       ('Men''s Dress Shirt', 49.99, 2, 'A sophisticated dress shirt for formal occasions.', 'mens-dress-shirt.jpg', 40, FALSE, 'White'),
       ('Women''s Dress', 79.99, 2, 'A beautiful and elegant dress for special occasions.', 'womens-dress.jpg', 50, FALSE, 'Mint'),
       ('Women''s Jeans', 69.99, 2, 'Comfortable and stylish jeans for a fashionable look.', 'womens-jeans.jpg', 30, TRUE, 'Blue'),
       ('Women''s Blouse', 49.99, 2, 'A versatile and chic blouse for everyday wear.', 'womens-blouse.jpg', 40, FALSE, 'Lavender'),
       ('Cookware Set', 149.99, 3, 'A comprehensive set of high-quality cookware for all your culinary needs.', 'cookware-set.jpg', 50, TRUE, 'Red'),
       ('Coffee Maker', 79.99, 3, 'Brew your favorite coffee with this efficient and stylish coffee maker.', 'coffee-maker.jpg', 30, FALSE, 'Black'),
       ('Kitchen Knife Set', 59.99, 3, 'A set of sharp and durable knives for effortless food preparation.', 'knife-set.jpg', 40, TRUE, 'Silver');
