package server.com.inventory.dao;

import server.com.inventory.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/inventory";
    private final String jdbcUsername = "root";  // Replace with your DB username
    private final String jdbcPassword = "123456";      // Replace with your DB password

    // Database Connection
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    // Add a product
    public void addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO products (name, category, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getCategory());
            statement.setInt(3, product.getQuantity());
            statement.setDouble(4, product.getPrice());
            statement.executeUpdate();
        }
    }

    // Update a product
    public void updateProduct(Product product) throws SQLException {
        String sql = "UPDATE products SET name = ?, category = ?, quantity = ?, price = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getCategory());
            statement.setInt(3, product.getQuantity());
            statement.setDouble(4, product.getPrice());
            statement.setInt(5, product.getId());
            statement.executeUpdate();
        }
    }

    // Delete a product
    public void deleteProduct(int id) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // Retrieve all products
    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("category"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("price")
                );
                products.add(product);
            }
        }
        return products;
    }

    // Find products by name or category
    public List<Product> findProducts(String keyword) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name LIKE ? OR category LIKE ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + keyword + "%");
            statement.setString(2, "%" + keyword + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("category"),
                            resultSet.getInt("quantity"),
                            resultSet.getDouble("price")
                    );
                    products.add(product);
                }
            }
        }
        return products;
    }
}
