CREATE SCHEMA IF NOT EXISTS Shop;

CREATE TABLE IF NOT EXISTS Shop.Product (
  product_id INT PRIMARY KEY,
  name       VARCHAR(50),
  price      DECIMAL(8, 2)
);
