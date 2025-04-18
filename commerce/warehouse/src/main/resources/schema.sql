CREATE TABLE IF NOT EXISTS warehouse_product (
    id VARCHAR PRIMARY KEY,
    fragile BOOLEAN,
    width DOUBLE,
    height DOUBLE,
    depth DOUBLE,
    weight DOUBLE,
    quantity INTEGER
);

CREATE TABLE IF NOT EXISTS order_booking (
    id VARCHAR PRIMARY KEY,
    delivery_id VARCHAR
);

CREATE TABLE IF NOT EXISTS booking_products (
    booking_id VARCHAR REFERENCES order_booking(id),
    product_id VARCHAR REFERENCES shopping_cart(id),
    PRIMARY KEY(booking_id, product_id)
);