package edu.school21.repositories;

import edu.school21.models.Product;

import java.util.Optional;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    private static final String FIND_ALL = "SELECT * FROM shop.product";
    private static final String FIND_BY_ID = "SELECT * FROM shop.product WHERE product.product_id=";
    private static final String UPDATE = "UPDATE shop.product SET name = ?, price = ? WHERE product_id = ?";
    private static final String SAVE = "INSERT INTO shop.product(NAME, PRICE) VALUES (?, ?)";
    private static final String DELETE = "DELETE FROM shop.product WHERE product_id = ";
    private final Connection connection;
    public ProductsRepositoryJdbcImpl(DataSource dataSource) throws SQLException {
        connection = dataSource.getConnection();
    }
    @Override
    public List<Product> findAll() {
        try (PreparedStatement ps = connection.prepareStatement(FIND_ALL)) {
            List<Product> products = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product(rs.getLong("product_id"), rs.getString("name"), rs.getLong("price"));
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Optional<Product> findById(Long product_id) {
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID + product_id)) {
            Product product;
            ResultSet rs = ps.executeQuery();
            if (!rs.next())
                return Optional.empty();
            product = new Product(rs.getLong("product_id"), rs.getString("name"), rs.getLong("price"));
            return Optional.of(product);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void update(Product product) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setString(1, product.getName());
            ps.setLong(2, product.getPrice());
            ps.setLong(3, product.getId());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void save(Product product) {
        try (PreparedStatement ps = connection.prepareStatement(SAVE)) {
            ps.setString(1, product.getName());
            ps.setLong(2, product.getPrice());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void delete(Long product_id) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE + product_id)) {
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
