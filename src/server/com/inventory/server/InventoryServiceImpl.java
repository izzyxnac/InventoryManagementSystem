package server.com.inventory.server;

import server.com.inventory.dao.ProductDAO;
import server.com.inventory.model.Product;
import server.com.inventory.util.LoggerUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.List;
import java.util.logging.Logger;

public class InventoryServiceImpl extends UnicastRemoteObject implements InventoryService {
    
    private static final long serialVersionUID = 1L;
    private final ProductDAO productDAO;
    private static final Logger logger = LoggerUtil.getLogger();

    public InventoryServiceImpl() throws RemoteException {
        super();
        productDAO = new ProductDAO();
    }

    @Override
    public boolean authenticate(String username, String password) throws RemoteException {
        logger.info("Authentication attempt for user: " + username);
        String sql = "SELECT password_hash FROM users WHERE username = ?";
        try (Connection connection = productDAO.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String storedHash = resultSet.getString("password_hash");
                    boolean isAuthenticated = storedHash.equals(password);
                    logger.info("Authentication " + (isAuthenticated ? "successful" : "failed") + " for user: " + username);
                    return isAuthenticated;
                }
            }
        } catch (SQLException e) {
            logger.severe("Authentication failed for user: " + username + " due to SQL error: " + e.getMessage());
            throw new RemoteException("Failed to authenticate user", e);
        }
        logger.warning("Authentication failed: user " + username + " not found.");
        return false;
    }

    @Override
    public void addProduct(Product product) throws RemoteException {
        try {
            productDAO.addProduct(product);
            logger.info("Added product: " + product);
        } catch (SQLException e) {
            logger.severe("Failed to add product: " + product + " due to SQL error: " + e.getMessage());
            throw new RemoteException("Failed to add product", e);
        }
    }

    @Override
    public void updateProduct(Product product) throws RemoteException {
        try {
            productDAO.updateProduct(product);
            logger.info("Updated product: " + product);
        } catch (SQLException e) {
            logger.severe("Failed to update product: " + product + " due to SQL error: " + e.getMessage());
            throw new RemoteException("Failed to update product", e);
        }
    }

    @Override
    public void deleteProduct(int id) throws RemoteException {
        try {
            productDAO.deleteProduct(id);
            logger.info("Deleted product with ID: " + id);
        } catch (SQLException e) {
            logger.severe("Failed to delete product with ID: " + id + " due to SQL error: " + e.getMessage());
            throw new RemoteException("Failed to delete product", e);
        }
    }

    @Override
    public List<Product> getAllProducts() throws RemoteException {
        try {
            List<Product> products = productDAO.getAllProducts();
            logger.info("Retrieved all products. Count: " + products.size());
            return products;
        } catch (SQLException e) {
            logger.severe("Failed to retrieve products due to SQL error: " + e.getMessage());
            throw new RemoteException("Failed to retrieve products", e);
        }
    }

    @Override
    public List<Product> findProducts(String keyword) throws RemoteException {
        try {
            List<Product> products = productDAO.findProducts(keyword);
            logger.info("Search for keyword '" + keyword + "' returned " + products.size() + " results.");
            return products;
        } catch (SQLException e) {
            logger.severe("Failed to search for products with keyword '" + keyword + "' due to SQL error: " + e.getMessage());
            throw new RemoteException("Failed to search products", e);
        }
    }
}
