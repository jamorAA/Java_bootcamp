CREATE SCHEMA IF NOT EXISTS Shop;

CREATE TABLE IF NOT EXISTS Shop.Product (
  product_id INT PRIMARY KEY IDENTITY,
  name       VARCHAR(50),
  price      INT
);

CREATE TABLE IF NOT EXISTS Shop.User (
    user_id     INT PRIMARY KEY IDENTITY,
    login          VARCHAR(50),
    password       VARCHAR(50),
    authentication BOOLEAN NOT NULL
);
