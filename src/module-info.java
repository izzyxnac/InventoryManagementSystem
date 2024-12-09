/**
 * 
 */
/**
 * 
 */
module InventoryManagementSystem {
    requires java.rmi;
    requires javafx.swing;
    requires transitive java.sql;
    requires transitive java.desktop;
    
    exports server.com.inventory.server;
    exports server.com.inventory.model;
    exports client.com.inventory.client;
    exports server.com.inventory.dao;
}
