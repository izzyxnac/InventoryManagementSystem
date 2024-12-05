package server.com.inventory.util;

import server.com.inventory.dao.ProductDAO;
import server.com.inventory.model.Product;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();

        try {
            // Test: Add a new product
            Product newProduct = new Product(0, "TestProduct", "TestCategory", 10, 50.99);
            productDAO.addProduct(newProduct);
            System.out.println("Product added successfully!");

            // Test: Retrieve all products
            List<Product> products = productDAO.getAllProducts();
            System.out.println("All Products:");
            for (Product product : products) {
                System.out.println(product);
            }

            // Test: Update a product
            if (!products.isEmpty()) {
                Product firstProduct = products.get(0);
                firstProduct.setName("UpdatedName");
                firstProduct.setPrice(99.99);
                productDAO.updateProduct(firstProduct);
                System.out.println("Product updated successfully!");
            }

            // Test: Search products by keyword
            List<Product> searchedProducts = productDAO.findProducts("Test");
            System.out.println("Search Results:");
            for (Product product : searchedProducts) {
                System.out.println(product);
            }

            // Test: Delete a product
            if (!products.isEmpty()) {
                productDAO.deleteProduct(products.get(0).getId());
                System.out.println("Product deleted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Database operation failed: " + e.getMessage());
        }
    }
}
