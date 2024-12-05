package server.com.inventory.server;

import server.com.inventory.model.Product;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface InventoryService extends Remote {
    
    // Method to authenticate a user
    boolean authenticate(String username, String password) throws RemoteException;

    // Method to add a product
    void addProduct(Product product) throws RemoteException;

    // Method to update a product
    void updateProduct(Product product) throws RemoteException;

    // Method to delete a product
    void deleteProduct(int id) throws RemoteException;

    // Method to retrieve all products
    List<Product> getAllProducts() throws RemoteException;

    // Method to search for products
    List<Product> findProducts(String keyword) throws RemoteException;
}
