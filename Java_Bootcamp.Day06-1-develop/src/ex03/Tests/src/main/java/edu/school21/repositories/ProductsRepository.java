package edu.school21.repositories;

import edu.school21.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductsRepository {
    List<Product> findAll();
    Optional<Product> findById(Long product_id);
    void update(Product product);
    void save(Product product);
    void delete(Long product_id);
}
