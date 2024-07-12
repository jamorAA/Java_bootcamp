package edu.school21.models;

import java.util.Objects;

public class Product {
    private Long product_id;
    private String name;
    private Long price;

    public Product(Long product_id, String name, Long price) {
        this.product_id = product_id;
        this.name = name;
        this.price = price;
    }
    public Long getId() {
        return product_id;
    }
    public void setId(Long product_id) {
        this.product_id = product_id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getPrice() {
        return price;
    }
    public void setPrice(Long price) {
        this.price = price;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Product product = (Product) o;
        return Objects.equals(product_id, product.product_id) && Objects.equals(name, product.name) && Objects.equals(price, product.price);
    }
    @Override
    public int hashCode() {
        return Objects.hash(product_id, name, price);
    }
    @Override
    public String toString() {
        return "Product: {" + "product_id=" + product_id + ", name='" + name + '\'' + ", price=" + price + '}';
    }
}
