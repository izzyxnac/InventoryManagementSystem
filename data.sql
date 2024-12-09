-- Sample data for the products table
INSERT INTO products (name, category, quantity, price) VALUES 
('Widget', 'Tools', 50, 19.99),
('Gadget', 'Electronics', 30, 99.99),
('Bolt', 'Hardware', 100, 0.10);

-- Sample data for the users table 
INSERT INTO users (username, password_hash) VALUES 
('admin', 'password'), -- password: password
('employee', '123456'); -- password: 123456
