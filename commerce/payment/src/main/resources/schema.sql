CREATE TABLE IF NOT EXISTS payment (
    id VARCHAR PRIMARY KEY,
    order_id VARCHAR,
    product_cost DOUBLE,
    delivery_cost DOUBLE,
    total_cost DOUBLE,
    status VARCHAR
);