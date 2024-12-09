package client.com.inventory.client;

import server.com.inventory.model.Product;
import server.com.inventory.server.InventoryService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.rmi.Naming;
import java.util.List;

public class InventoryGUI extends JFrame {
    private static final long serialVersionUID = 1L;

    private InventoryService inventoryService;

    private JTable productTable;
    private DefaultTableModel tableModel;

    public InventoryGUI(InventoryService inventoryService) {
        this.inventoryService = inventoryService;

        setTitle("Inventory Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Create table
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Category", "Quantity", "Price"}, 0);
        productTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(productTable);
        add(scrollPane, BorderLayout.CENTER);

        // Add search bar
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        JTextField searchField = new JTextField();
        JButton searchButton = new JButton("Search");
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);
        add(searchPanel, BorderLayout.NORTH);

        // Search button action
        searchButton.addActionListener(e -> searchProducts(searchField.getText()));

        // Create buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Product");
        JButton updateButton = new JButton("Update Product");
        JButton deleteButton = new JButton("Delete Product");
        JButton refreshButton = new JButton("Refresh");
        JButton logoutButton = new JButton("Logout");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(logoutButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add button actions
        addButton.addActionListener(e -> openAddProductDialog());
        updateButton.addActionListener(e -> openUpdateProductDialog());
        deleteButton.addActionListener(e -> deleteSelectedProduct());
        refreshButton.addActionListener(e -> refreshProductTable());
        logoutButton.addActionListener(e -> logout());

        // Load initial data
        refreshProductTable();
    }

    private void openAddProductDialog() {
        // Show dialog for adding a new product
        ProductDialog dialog = new ProductDialog(this, inventoryService, null);
        dialog.setVisible(true);
        refreshProductTable();
    }

    private void openUpdateProductDialog() {
        // Show dialog for updating an existing product
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product to update");
            return;
        }

        int productId = (int) tableModel.getValueAt(selectedRow, 0);
        String name = (String) tableModel.getValueAt(selectedRow, 1);
        String category = (String) tableModel.getValueAt(selectedRow, 2);
        int quantity = (int) tableModel.getValueAt(selectedRow, 3);
        double price = (double) tableModel.getValueAt(selectedRow, 4);

        Product product = new Product(productId, name, category, quantity, price);
        ProductDialog dialog = new ProductDialog(this, inventoryService, product);
        dialog.setVisible(true);
        refreshProductTable();
    }

    private void deleteSelectedProduct() {
        // Delete the selected product
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product to delete");
            return;
        }

        int productId = (int) tableModel.getValueAt(selectedRow, 0);
        try {
            inventoryService.deleteProduct(productId);
            JOptionPane.showMessageDialog(this, "Product deleted successfully");
            refreshProductTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to delete product", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void refreshProductTable() {
        // Refresh the product table with data from the server
        try {
            List<Product> products = inventoryService.getAllProducts();
            tableModel.setRowCount(0); // Clear existing rows
            for (Product product : products) {
                tableModel.addRow(new Object[]{
                        product.getId(),
                        product.getName(),
                        product.getCategory(),
                        product.getQuantity(),
                        product.getPrice()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load products", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void searchProducts(String keyword) {
        // Search for products by keyword
        try {
            List<Product> products = inventoryService.findProducts(keyword);
            tableModel.setRowCount(0); // Clear existing rows
            for (Product product : products) {
                tableModel.addRow(new Object[]{
                        product.getId(),
                        product.getName(),
                        product.getCategory(),
                        product.getQuantity(),
                        product.getPrice()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to search products", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void logout() {
        // Logout the user and exit the application
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose(); // Close the main window
            System.exit(0); // Exit the application
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Connect to RMI service
                InventoryService inventoryService = (InventoryService) Naming.lookup("rmi://localhost/InventoryService");

                // Show login dialog
                LoginDialog loginDialog = new LoginDialog(null, inventoryService);
                loginDialog.setVisible(true);

                if (!loginDialog.isAuthenticated()) {
                    System.exit(0); // Exit if not authenticated
                }

                // Show main GUI
                InventoryGUI gui = new InventoryGUI(inventoryService);
                gui.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error connecting to Inventory Service", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                System.exit(0);
            }
        });
    }
}
