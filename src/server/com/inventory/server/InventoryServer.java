package server.com.inventory.server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class InventoryServer {
    public static void main(String[] args) {
        try {
            // Start RMI registry
            LocateRegistry.createRegistry(1099);

            // Create and bind the InventoryService
            InventoryService inventoryService = new InventoryServiceImpl();
            Naming.rebind("rmi://localhost/InventoryService", inventoryService);

            System.out.println("Inventory Service is running...");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Server exception: " + e.getMessage());
        }
    }
}
