CREATE TABLE IF NOT EXISTS warehouse_product (
    id VARCHAR PRIMARY KEY,
    fragile BOOLEAN,
    width DOUBLE,
    height DOUBLE,
    depth DOUBLE,
    weight DOUBLE,
    quantity INTEGER
);