CREATE TABLE IF NOT EXISTS delivery (
    id VARCHAR PRIMARY KEY,
    state VARCHAR,
    order_id VARCHAR,
    from_address BIGINT REFERENCES address(id),
    to_address BIGINT REFERENCES address(id)
);

CREATE TABLE IF NOT EXISTS address (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    country VARCHAR,
    city VARCHAR,
    street VARCHAR,
    house VARCHAR,
    flat VARCHAR
);