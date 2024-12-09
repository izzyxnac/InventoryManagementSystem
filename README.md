---
# **Inventory Management System / Système de Gestion d'Inventaire**
---

## **Description**

###**English**  
The **Inventory Management System** is a distributed application designed to manage product inventory using **Java RMI** for client-server communication. The system supports both **graphical (GUI)** and **console-based interfaces**, allowing users to:  
• Log in securely with authentication.  
• Perform CRUD (Create, Read, Update, Delete) operations on products.  
• Search for products by name or category.  
• Log out of the system.  
• Log all operations for traceability.

###**Français**  
Le **Système de Gestion d'Inventaire** est une application distribuée conçue pour gérer l'inventaire des produits à l'aide de **Java RMI** pour la communication client-serveur. Le système prend en charge les interfaces **graphiques (GUI)** et **basées sur la console**, permettant aux utilisateurs de :  
• Se connecter en toute sécurité avec une authentification.  
• Effectuer des opérations CRUD (Créer, Lire, Mettre à jour, Supprimer) sur les produits.  
• Rechercher des produits par nom ou catégorie.  
• Se déconnecter du système.  
• Enregistrer toutes les opérations pour assurer une traçabilité.

---

## **Features / Fonctionnalités**

1. **Login / Connexion**: Secure user authentication before accessing the inventory / Authentification sécurisée des utilisateurs.
2. **Product Management / Gestion des Produits**: Add, update, delete, and view products / Ajouter, mettre à jour, supprimer et afficher des produits.
3. **Search / Recherche**: Quickly locate products using a keyword search / Rechercher rapidement des produits en utilisant des mots-clés.
4. **Logout / Déconnexion**: Securely exit the system after use / Quittez le système en toute sécurité après utilisation.
5. **Logging / Journalisation**: Record all user actions in `inventory_operations.log` / Enregistrez toutes les actions des utilisateurs dans `inventory_operations.log`.
6. **Dual Interfaces / Interfaces Doubles**:  
   o A **GUI-based interface** built with Swing / Une interface graphique basée sur Swing.  
   o A **console-based interface** for lightweight interactions / Une interface basée sur la console pour des interactions légères.

---

## **Project Structure / Structure du Projet**

```
InventoryManagementSystem/
├── src/
│   ├── client.com.inventory.client/
│   │   ├── InventoryClient.java     # Console-based client / Client basé sur la console
│   │   ├── InventoryGUI.java        # GUI-based client / Client avec interface graphique
│   │   ├── LoginDialog.java         # Login dialog for GUI / Boîte de dialogue de connexion
│   │   ├── ProductDialog.java       # Dialog for adding/updating products / Dialogue pour l'ajout/mise à jour des produits
│   │   └── package-info.java
│   ├── server.com.inventory.dao/
│   │   ├── ProductDAO.java          # Database access object for products / DAO pour les produits
│   │   └── package-info.java
│   ├── server.com.inventory.model/
│   │   ├── Product.java             # Model representing a product / Modèle représentant un produit
│   │   └── package-info.java
│   ├── server.com.inventory.server/
│   │   ├── InventoryServer.java     # Server for RMI / Serveur RMI
│   │   ├── InventoryService.java    # RMI interface / Interface RMI
│   │   ├── InventoryServiceImpl.java # RMI implementation / Implémentation RMI
│   │   └── package-info.java
│   ├── server.com.inventory.util/
│   │   ├── LoggerUtil.java          # Utility for logging / Utilitaire de journalisation
│   │   └── package-info.java
├── lib/
│   ├── mysql-connector-j-9.1.0.jar  # MySQL JDBC driver / Pilote JDBC MySQL
│   ├── javafx.swing.jar             # JavaFX Swing integration / Intégration JavaFX Swing
├── inventory_operations.log         # Log file for operations / Fichier de log des opérations
├── module-info.java                 # Module configuration / Configuration du module
```

---

## **Technologies Used / Technologies Utilisées**

• **Java SE 23**: Core functionality and RMI communication / Fonctionnalité principale et communication RMI.  
• **Swing**: For graphical user interface / Pour l'interface graphique.  
• **JavaFX Swing JAR**: Added for Swing and JavaFX integration / Ajouté pour l'intégration Swing et JavaFX.  
• **MySQL**: Centralized relational database for storing data / Base de données relationnelle centralisée pour le stockage des données.  
• **Java RMI**: Enables client-server communication / Permet la communication client-serveur.  
• **Logging**: Tracks all user operations for traceability / Suit toutes les opérations des utilisateurs pour la traçabilité.

---

## **Setup Instructions / Instructions de Configuration**

### **Database Setup / Configuration de la Base de Données (`schema.sql`,`data.sql`)**

**English / Français** :  
• Create the database / Créez la base de données :

```sql
CREATE DATABASE inventory;
```

• Create the tables / Créez les tables:

```sql
CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    category VARCHAR(100),
    quantity INT,
    price DECIMAL(10, 2)
);

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    password_hash VARCHAR(255)
);
```

• Insert sample data / Insérez des données d'exemple:

```sql
INSERT INTO products (name, category, quantity, price) VALUES
('Widget', 'Tools', 50, 19.99),
('Gadget', 'Electronics', 30, 99.99),
('Bolt', 'Hardware', 100, 0.10);

INSERT INTO users (username, password_hash) VALUES
('admin', 'password'), -- password: password
('employee', '123456'); -- password: 123456
```

• Configure the file `ProductDAO.java`/Configurez le fichier `ProductDAO.java` :

```java
private final String jdbcURL = "jdbc:mysql://localhost:3306/inventory";
private final String jdbcUsername = "root";  // Replace with your user /Remplacez par votre utilisateur
private final String jdbcPassword = "";      // Replace with your password /Remplacez par votre mot de passe
```

---

## **How to Run the Application / Exécution de l'Application**

### **Server-Side / Côté Serveur**

• Compile and run the `InventoryServer` class / Compilez et exécutez la classe `InventoryServer`:

```bash
java server.com.inventory.server.InventoryServer
```

### **Client-Side / Côté Client**

**Option 1: Graphical User Interface (GUI) / Interface Graphique (GUI)**  
• Run the `InventoryGUI` class / Exécutez la classe `InventoryGUI`:

```bash
java client.com.inventory.client.InventoryGUI
```

**Option 2: Console-Based Interface / Interface Basée sur la Console**  
• Run the `InventoryClient` class / Exécutez la classe `InventoryClient`:

```bash
java client.com.inventory.client.InventoryClient
```

---

## **Author / Auteur**

- **NACIRI Issam**
- **EL KHANCHOUFI Youssef**

---

## **License**

This project is distributed under the [MIT License](https://opensource.org/licenses/MIT).
