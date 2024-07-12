package edu.school21.repositories;

import edu.school21.models.Product;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ProductsRepositoryJdbcImplTest {
    private static final List<Product> EXPECTED_FIND_ALL_PRODUCTS = Arrays.asList(
            new Product(1L, "milk", 20L),
            new Product(2L, "fish", 50L),
            new Product(3L, "pen", 1L),
            new Product(4L, "computer", 4500L),
            new Product(5L, "soap", 3L));
    private static final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(5L, "soap", 3L);
    private static final Product EXPECTED_UPDATED_PRODUCT = new Product(4L, "computer", 4500L);
    private static final Product EXPECTED_SAVED_PRODUCT = new Product(3L, "pen", 1L);
    private EmbeddedDatabase dataSource;
    private ProductsRepositoryJdbcImpl productsRepositoryJdbc;

    @BeforeEach
    public void init() throws SQLException {
        dataSource = new EmbeddedDatabaseBuilder().addScript("schema.sql").addScript("data.sql").build();
        productsRepositoryJdbc = new ProductsRepositoryJdbcImpl(dataSource);
    }
    @Test
    public void testForAll() {
        List<Product> actual = productsRepositoryJdbc.findAll();
        assertEquals(EXPECTED_FIND_ALL_PRODUCTS, actual);
    }
    @Test
    public void testForId() {
        assertEquals(productsRepositoryJdbc.findById(5L).get(), EXPECTED_FIND_BY_ID_PRODUCT);
    }
    @Test
    public void updateTest() {
        productsRepositoryJdbc.update(new Product(4L, "computer", 4500L));
        assertEquals(EXPECTED_UPDATED_PRODUCT, productsRepositoryJdbc.findById(4L).get());
    }
    @Test
    public void saveTest() {
        productsRepositoryJdbc.save(new Product(3L, "pen", 1L));
        assertEquals(EXPECTED_SAVED_PRODUCT, productsRepositoryJdbc.findById(3L).get());
    }
    @Test
    public void deleteTest() {
        productsRepositoryJdbc.delete(2L);
        assertFalse(productsRepositoryJdbc.findById(2L).isPresent());
    }
    @AfterEach
    void close() {
        dataSource.shutdown();
    }
}
