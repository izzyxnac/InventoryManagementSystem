package client.com.inventory.client;


import server.com.inventory.model.Product;
import server.com.inventory.server.InventoryService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ProductDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private JTextField nameField;
    private JTextField categoryField;
    private JTextField quantityField;
    private JTextField priceField;
    private InventoryService inventoryService;
    private Product product;

    public ProductDialog(JFrame parent, InventoryService inventoryService, Product product) {
        super(parent, true);
        this.inventoryService = inventoryService;
        this.product = product;

        setTitle(product == null ? "Add Product" : "Update Product");
        setSize(400, 300);
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Name:"));
        nameField = new JTextField(product != null ? product.getName() : "");
        add(nameField);

        add(new JLabel("Category:"));
        categoryField = new JTextField(product != null ? product.getCategory() : "");
        add(categoryField);

        add(new JLabel("Quantity:"));
        quantityField = new JTextField(product != null ? String.valueOf(product.getQuantity()) : "");
        add(quantityField);

        add(new JLabel("Price:"));
        priceField = new JTextField(product != null ? String.valueOf(product.getPrice()) : "");
        add(priceField);

        JButton saveButton = new JButton("Save");
        add(saveButton);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveProduct();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        add(cancelButton);
        cancelButton.addActionListener(e -> dispose());
    }

    private void saveProduct() {
        String name = nameField.getText();
        String category = categoryField.getText();
        int quantity;
        double price;

        try {
            quantity = Integer.parseInt(quantityField.getText());
            price = Double.parseDouble(priceField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input for quantity or price", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (product == null) { // Adding a new product
                inventoryService.addProduct(new Product(0, name, category, quantity, price));
                JOptionPane.showMessageDialog(this, "Product added successfully");
            } else { // Updating an existing product
                product.setName(name);
                product.setCategory(category);
                product.setQuantity(quantity);
                product.setPrice(price);
                inventoryService.updateProduct(product);
                JOptionPane.showMessageDialog(this, "Product updated successfully");
            }
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to save product", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
