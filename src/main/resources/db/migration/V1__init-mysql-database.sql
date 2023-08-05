-- Drop tables if they exist
DROP TABLE IF EXISTS beer;
DROP TABLE IF EXISTS customer;

-- Create table beer
CREATE TABLE beer (
    id UUID NOT NULL,
    beer_name VARCHAR(50) NOT NULL,
    beer_style SMALLINT NOT NULL,
    created_date TIMESTAMP(6),
    price NUMERIC(38,2) NOT NULL,
    quantity_on_hand INTEGER,
    upc VARCHAR(255) NOT NULL,
    update_date TIMESTAMP(6),
    version INTEGER,
    PRIMARY KEY (id)
);

-- Create table customer
CREATE TABLE customer (
    id UUID NOT NULL,
    created_date TIMESTAMP(6),
    name VARCHAR(255),
    update_date TIMESTAMP(6),
    version INTEGER,
    PRIMARY KEY (id)
);
