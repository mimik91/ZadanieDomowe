DROP TABLE IF EXISTS products CASCADE;


DROP TABLE IF EXISTS product CASCADE;
CREATE TABLE product (
       id SERIAL,
       name TEXT,
       price numeric,
       PRIMARY KEY (id)
);
