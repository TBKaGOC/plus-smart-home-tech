CREATE TABLE IF NOT EXISTS store_product (
    id VARCHAR PRIMARY KEY,
    name VARCHAR,
    description VARCHAR,
    image_src VARCHAR,
    price FLOAT,
    state VARCHAR,
    quantity VARCHAR,
    category VARCHAR
);