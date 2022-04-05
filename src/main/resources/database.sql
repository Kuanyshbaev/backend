CREATE TABLE products(
    id    INTEGER AUTO_INCREMENT PRIMARY KEY,
    name  VARCHAR(255),
    price DECIMAL
);

CREATE TABLE orders
(
    id         INTEGER AUTO_INCREMENT PRIMARY KEY,
    product_id INTEGER,
    amount     INTEGER,
    CONSTRAINT orders_products_fk FOREIGN KEY (product_id)
        REFERENCES products (id) ON DELETE CASCADE
);
ALTER TABLE orders ADD CONSTRAINT orders_products_fk FOREIGN KEY (product_id)
    REFERENCES products (id) ON DELETE CASCADE;

INSERT INTO products(name, price)
VALUES ('Шаурма ассорти', 1300),
       ('Шаурма с курицей', 1200),
       ('Шаурма с мясом', 1400),
       ('Шаурма в тарелке', 1200),
       ('Шаурма в багете', 1400),
       ('Шаурма с фирменным соусом', 1500);
