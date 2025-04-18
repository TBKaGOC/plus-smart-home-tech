CREATE TABLE IF NOT EXISTS shop_order (
    id VARCHAR PRIMARY KEY,
    shopping_cart_id VARCHAR,
    payment_id VARCHAR,
    delivery_id VARCHAR,
    state VARCHAR,
    delivery_weight DOUBLE,
    delivery_volume DOUBLE,
    fragile BOOLEAN,
    delivery_price DOUBLE,
    product_price DOUBLE,
    total_price DOUBLE,
    username VARCHAR
);

create TABLE IF NOT EXISTS order_product (
    id VARCHAR PRIMARY KEY,
    quantity INTEGER
);

CREATE TABLE IF NOT EXISTS order_products (
    order_id VARCHAR REFERENCES products_order(id),
    product_id VARCHAR REFERENCES order_product(id)
);
