package client.com.inventory.client;

import server.com.inventory.server.InventoryService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private boolean authenticated = false;
	@SuppressWarnings("unused")
    private InventoryService inventoryService;

    public LoginDialog(JFrame parent, InventoryService inventoryService) {
        super(parent, "Login", true);
        this.inventoryService = inventoryService;

        setSize(300, 150);
        setLayout(new GridLayout(3, 2));

        // Add fields
        add(new JLabel("Username:"));
        JTextField usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        add(passwordField);

        JButton loginButton = new JButton("Login");
        add(loginButton);

        JButton cancelButton = new JButton("Cancel");
        add(cancelButton);

        // Login button action
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                try {
                    if (inventoryService.authenticate(username, password)) {
                        authenticated = true;
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(LoginDialog.this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(LoginDialog.this, "Error during authentication", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        // Cancel button action
        cancelButton.addActionListener(e -> dispose());
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}
