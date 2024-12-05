# Inventory Management System

## Description
Le Système de Gestion d'Inventaire est une application distribuée basée sur Java RMI. Elle permet de gérer les produits d'une base de données MySQL, avec des fonctionnalités telles que :
- CRUD (Créer, Lire, Mettre à jour, Supprimer) sur les produits.
- Authentification utilisateur.
- Journaux d'opérations.

## Structure du Projet
- `client/` : Contient le code client permettant de se connecter au serveur via RMI.
- `server/` :
  - `dao/` : Contient la logique d'accès à la base de données (DAO).
  - `model/` : Contient les modèles de données (par ex., `Product`).
  - `server/` : Contient le serveur RMI et ses implémentations.
  - `util/` : Contient des utilitaires comme le système de logs.
- `lib/` : Bibliothèque externe nécessaire (`mysql-connector-java`).

## Instructions pour l'Installation
### Pré-requis
- Java 22 ou supérieur
- MySQL 8.0 ou supérieur
- Un IDE comme Eclipse ou IntelliJ

### Étapes de Configuration
1. Configurez votre base de données MySQL :
   - Exécutez `schema.sql` pour créer les tables.
   - Exécutez `data.sql` pour insérer les données initiales.

2. Configurez le fichier `ProductDAO.java` :
   ```java
   private final String jdbcURL = "jdbc:mysql://localhost:3306/inventory";
   private final String jdbcUsername = "root";  // Remplacez par votre utilisateur
   private final String jdbcPassword = "";      // Remplacez par votre mot de passe
   ```

3. Compilez et exécutez :
   - **Serveur** : `InventoryServer.java`
   - **Client** : `InventoryClient.java`

## Fonctionnalités
- Authentification sécurisée des utilisateurs.
- Gestion des produits :
  - Ajouter, Mettre à jour, Supprimer.
  - Rechercher par nom ou catégorie.
- Journaux : Toutes les opérations sont enregistrées dans `inventory_operations.log`.

## Auteurs
- **NACIRI Issam**
- **EL KHANCHOUFI Youssef**
