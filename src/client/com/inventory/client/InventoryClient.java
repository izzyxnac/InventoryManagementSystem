package client.com.inventory.client;

import server.com.inventory.model.Product;
import server.com.inventory.server.InventoryService;

import java.rmi.Naming;
import java.util.List;
import java.util.Scanner;

public class InventoryClient {
    public static void main(String[] args) {
        try {
            // Connect to the RMI server
            InventoryService inventoryService = (InventoryService) Naming.lookup("rmi://localhost/InventoryService");
            System.out.println("Connected to Inventory Service");

            // Authenticate user
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter username: ");
            String username = scanner.next();
            System.out.print("Enter password: ");
            String password = scanner.next();

            if (!inventoryService.authenticate(username, password)) {
                System.out.println("Authentication failed! Exiting...");
                System.exit(0);
            }
            System.out.println("Authentication successful!");

            // Display menu
            while (true) {
                System.out.println("\nChoose an option:");
                System.out.println("1. Add Product");
                System.out.println("2. Update Product");
                System.out.println("3. Delete Product");
                System.out.println("4. View All Products");
                System.out.println("5. Search Products");
                System.out.println("6. Exit");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        // Add a product
                        System.out.print("Enter product name: ");
                        String name = scanner.next();
                        System.out.print("Enter category: ");
                        String category = scanner.next();
                        System.out.print("Enter quantity: ");
                        int quantity = scanner.nextInt();
                        System.out.print("Enter price: ");
                        double price = scanner.nextDouble();
                        Product newProduct = new Product(0, name, category, quantity, price);
                        inventoryService.addProduct(newProduct);
                        System.out.println("Product added successfully!");
                        break;
                    case 2:
                        // Update a product
                        System.out.print("Enter product ID to update: ");
                        int updateId = scanner.nextInt();
                        System.out.print("Enter new name: ");
                        name = scanner.next();
                        System.out.print("Enter new category: ");
                        category = scanner.next();
                        System.out.print("Enter new quantity: ");
                        quantity = scanner.nextInt();
                        System.out.print("Enter new price: ");
                        price = scanner.nextDouble();
                        Product updatedProduct = new Product(updateId, name, category, quantity, price);
                        inventoryService.updateProduct(updatedProduct);
                        System.out.println("Product updated successfully!");
                        break;
                    case 3:
                        // Delete a product
                        System.out.print("Enter product ID to delete: ");
                        int deleteId = scanner.nextInt();
                        inventoryService.deleteProduct(deleteId);
                        System.out.println("Product deleted successfully!");
                        break;
                    case 4:
                        // View all products
                        List<Product> products = inventoryService.getAllProducts();
                        System.out.println("All Products:");
                        for (Product product : products) {
                            System.out.println(product);
                        }
                        break;
                    case 5:
                        // Search products
                        System.out.print("Enter search keyword: ");
                        String keyword = scanner.next();
                        List<Product> searchedProducts = inventoryService.findProducts(keyword);
                        System.out.println("Search Results:");
                        for (Product product : searchedProducts) {
                            System.out.println(product);
                        }
                        break;
                    case 6:
                        // Exit
                        System.out.println("Exiting...");
                        scanner.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Client exception: " + e.getMessage());
        }
    }
}
