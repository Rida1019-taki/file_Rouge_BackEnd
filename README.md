# Tomobilty.ma — Backend

## 1. Nom du projet

**Tomobilty.ma — Backend**

---

## 2. Présentation du projet

Tomobilty.ma est une plateforme web de location de voitures permettant aux clients de rechercher et réserver des véhicules disponibles.
Le backend fournit une API REST permettant de gérer les utilisateurs, les voitures, les réservations, les catégories et les villes.
Il permet également de gérer les différents rôles de la plateforme : **Client, Owner et Administrateur**.
L'objectif principal est de fournir une API sécurisée, fiable et évolutive pour assurer le fonctionnement de la plateforme de location.

---

## 3. Problématique

Le processus traditionnel de location de voitures peut être difficile à gérer lorsqu'il faut rechercher les véhicules disponibles, vérifier leurs informations et gérer les réservations.

La solution proposée permet de centraliser ces opérations dans une API REST sécurisée. Les utilisateurs peuvent ainsi gérer leurs comptes, les propriétaires peuvent gérer leurs véhicules et les administrateurs peuvent superviser l'activité de la plateforme.

---

## 4. Fonctionnalités principales

* **Authentifier** les utilisateurs avec JWT.
* **Gérer** les utilisateurs selon leurs rôles.
* **Gérer** les voitures et leurs informations.
* **Rechercher** et filtrer les voitures disponibles.
* **Créer et gérer** les réservations.
* **Contrôler** les annonces et les utilisateurs avec le rôle Administrateur.

---

## 5. Technologies utilisées

| Technologie       | Utilisation                                   |
| ----------------- | --------------------------------------------- |
| Java              | Langage principal du backend                  |
| Spring Boot       | Développement de l'application backend        |
| Spring Security   | Sécurisation de l'API et gestion des rôles    |
| JWT               | Authentification et autorisation              |
| Spring Data JPA   | Accès aux données                             |
| Hibernate         | Mapping objet-relationnel                     |
| MySQL             | Stockage des données                          |
| Bean Validation   | Validation des données reçues par l'API       |
| REST API          | Communication entre le frontend et le backend |
| Swagger / OpenAPI | Documentation et test de l'API                |
| Maven             | Gestion des dépendances et du build           |
| Git / GitHub      | Versionnement du code                         |
| Postman           | Test des endpoints                            |

---

# 6. Architecture du projet

Le backend suit une architecture organisée par responsabilités :

```text
src/
└── main/
    ├── java/
    │   └── ...
    │       ├── controller/
    │       ├── service/
    │       ├── repository/
    │       ├── entity/
    │       ├── dto/
    │       ├── mapper/
    │       ├── security/
    │       ├── exception/
    │       └── config/
    │
    └── resources/
        ├── application.properties
        └── ...
```

Cette organisation permet de séparer la logique métier, l'accès aux données, les contrôleurs REST et la sécurité.

---

# 7. Installation et lancement

## 7.1 Prérequis

Pour utiliser le backend, vous devez disposer de :

* **Java 21**
* **Maven**
* **MySQL**
* **Git**
* **IntelliJ IDEA** ou un autre IDE Java
* **Postman** pour tester l'API

---

## 7.2 Cloner le dépôt

```bash
git clone LIEN_DU_DEPOT_BACKEND
```

Puis :

```bash
cd NOM_DU_PROJET_BACKEND
```

---

## 7.3 Configurer la base de données

Créer une base de données MySQL :

```sql
CREATE DATABASE tomobilty;
```

Configurer ensuite les informations de connexion dans :

```text
src/main/resources/application.properties
```

Exemple :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tomobilty
spring.datasource.username=root
spring.datasource.password=VOTRE_MOT_DE_PASSE

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> Les mots de passe et les informations sensibles ne doivent pas être publiés dans GitHub.

---

## 7.4 Variables d'environnement

Les informations sensibles peuvent être configurées avec des variables d'environnement.

Exemple :

```env
DB_URL=jdbc:mysql://localhost:3306/tomobilty
DB_USERNAME=root
DB_PASSWORD=
JWT_SECRET=
```

---

## 7.5 Installer les dépendances

Avec Maven :

```bash
mvn clean install
```

---

## 7.6 Lancer le backend

```bash
mvn spring-boot:run
```

Ou lancer directement l'application depuis IntelliJ IDEA.

Le backend sera généralement disponible à l'adresse :

```text
http://localhost:8080
```

---

# 8. API REST

L'API permet notamment de gérer :

### Authentification

```text
POST /api/auth/register
POST /api/auth/login
```

### Utilisateurs

```text
GET    /api/users
GET    /api/users/{id}
PUT    /api/users/{id}
DELETE /api/users/{id}
```

### Voitures

```text
GET    /api/cars
GET    /api/cars/{id}
POST   /api/cars
PUT    /api/cars/{id}
DELETE /api/cars/{id}
```

### Réservations

```text
GET    /api/reservations
GET    /api/reservations/{id}
POST   /api/reservations
PUT    /api/reservations/{id}
DELETE /api/reservations/{id}
```

> Les routes exactes doivent correspondre aux controllers présents dans le projet.

---

# 9. Sécurité

L'application utilise **Spring Security** et **JWT** pour sécuriser les ressources.

Les principaux rôles sont :

* **CLIENT**
* **OWNER**
* **ADMIN**

Les permissions sont contrôlées selon le rôle de l'utilisateur connecté.

Exemple :

```text
CLIENT
→ rechercher une voiture
→ consulter une voiture
→ créer une réservation
→ gérer ses réservations

OWNER
→ ajouter une voiture
→ modifier une voiture
→ supprimer une voiture
→ gérer ses réservations

ADMIN
→ gérer les utilisateurs
→ gérer les annonces
→ superviser les réservations
```

---

# 10. Base de données

Les principales entités du système sont :

* **Utilisateur**
* **Voiture**
* **Réservation**
* **Image**
* **Catégorie**
* **Ville**

Relations principales :

```text
Utilisateur (Owner)
        │
        │ possède
        ▼
     Voiture
        │
        │ possède
        ▼
  Réservation
        ▲
        │
        │ effectuée par
        │
     Client
```

Un owner peut posséder plusieurs voitures.

Une voiture peut avoir plusieurs réservations.

Un client peut effectuer plusieurs réservations.

Une réservation concerne une seule voiture.

---

# 11. Documentation Swagger

L'API peut être documentée avec **Swagger / OpenAPI** afin de faciliter la consultation et le test des endpoints.

Après le lancement du backend, accéder à :

```text
http://localhost:8080/swagger-ui/index.html
```

---

# 12. Tests

Les endpoints peuvent être testés avec **Postman**.

Les tests permettent notamment de vérifier :

* l'inscription ;
* la connexion ;
* la génération du JWT ;
* l'accès selon les rôles ;
* la création d'une voiture ;
* la modification d'une voiture ;
* la suppression d'une voiture ;
* la création d'une réservation ;
* l'annulation d'une réservation.

---

# 13. Difficultés rencontrées

## Difficulté 1 — Authentification et autorisation

### Problème rencontré

La gestion de l'authentification et des différents rôles nécessite de sécuriser correctement les endpoints de l'API.

### Recherches / Tests

Plusieurs tests ont été réalisés avec Spring Security, JWT et Postman afin de vérifier l'accès aux différentes ressources.

### Solution

L'authentification a été basée sur JWT et les accès ont été contrôlés selon le rôle de l'utilisateur.

### Ce que j'ai appris

Cette difficulté m'a permis de mieux comprendre le fonctionnement de Spring Security, des filtres JWT et du contrôle d'accès basé sur les rôles.

---

## Difficulté 2 — Gestion des réservations

### Problème rencontré

Une réservation doit respecter la disponibilité du véhicule et les dates sélectionnées par le client.

### Recherches / Tests

Des tests ont été réalisés pour vérifier les dates, les statuts et les différentes situations de réservation.

### Solution

La logique métier vérifie les informations de réservation avant de l'enregistrer et permet de gérer différents statuts.

### Ce que j'ai appris

Cette partie m'a permis de mieux comprendre la gestion des relations entre les entités et l'implémentation d'une logique métier avec Spring Boot.

---

# 14. Améliorations possibles

Dans une prochaine version, je pourrais :

* **Ajouter** un système de paiement en ligne.
* **Ajouter** des notifications pour les réservations.
* **Ajouter** des tests automatisés plus complets.
* **Améliorer** la gestion des images des véhicules.
* **Déployer** l'API sur une infrastructure cloud.

Ces améliorations permettraient de rendre la plateforme plus complète, sécurisée et adaptée à une utilisation réelle.

---

# 15. Auteur

**Rida Taki**

Projet : **Tomobilty.ma**

Développement Backend avec **Java / Spring Boot / MySQL**.
