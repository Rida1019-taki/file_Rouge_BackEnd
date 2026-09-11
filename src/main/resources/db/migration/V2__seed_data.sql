-- =============================================================
-- Tomobilty.ma - Données de test (seed)
-- Mots de passe:
--   - Tous les utilisateurs (sauf admin): 123456
--   - Admin: admin2026
-- =============================================================

-- ---------- Catégories ----------
INSERT INTO categories (id, nom, description) VALUES
    (1, 'CITADINE', 'Citadines économiques, idéales en ville'),
    (2, 'BERLINE', 'Berlines confortables pour les longs trajets'),
    (3, 'SUV', 'SUV spacieux adaptés à tous les terrains'),
    (4, 'SPORT', 'Voitures de sport haut de gamme'),
    (5, 'MINIVAN', 'Minivans pour les familles et les groupes'),
    (6, 'LUXE', 'Véhicules de prestige');

-- ---------- Villes ----------
INSERT INTO villes (id, nom) VALUES
    (1, 'Casablanca'),
    (2, 'Rabat'),
    (3, 'Marrakech'),
    (4, 'Tanger'),
    (5, 'Fès'),
    (6, 'Agadir');

-- ---------- Utilisateurs ----------
-- id 1-2: Clients, id 3-4: Owners, id 5: Admin
INSERT INTO utilisateurs (id, nom, prenom, email, telephone, mot_de_passe, role, actif) VALUES
    (1, 'Benali', 'Ahmed', 'client@test.com', '0611111111', '$2a$10$QFYddcdNGYyRloH9AXP55.Sdq6oIhzACYv8.WVCsQEAOZIkj8VUpe', 'CLIENT', TRUE),
    (2, 'El Amrani', 'Salma', 'client2@test.com', '0622222222', '$2a$10$QFYddcdNGYyRloH9AXP55.Sdq6oIhzACYv8.WVCsQEAOZIkj8VUpe', 'CLIENT', TRUE),
    (3, 'Tazi', 'Yassine', 'owner@test.com', '0633333333', '$2a$10$QFYddcdNGYyRloH9AXP55.Sdq6oIhzACYv8.WVCsQEAOZIkj8VUpe', 'OWNER', TRUE),
    (4, 'El Fassi', 'Karim', 'owner2@test.com', '0644444444', '$2a$10$QFYddcdNGYyRloH9AXP55.Sdq6oIhzACYv8.WVCsQEAOZIkj8VUpe', 'OWNER', TRUE),
    (5, 'Admin', 'Amine', 'admin@test.com', '0655555555', '$2a$10$LG2ox4OjZuV6oA9mKcpIy./B9ES7qIP3kJvOAyYAXxO6tWH8uFZBO', 'ADMIN', TRUE);

-- Tables filles (Inheritance JOINED)
INSERT INTO clients (id) VALUES (1), (2);
INSERT INTO owners (id, type_owner, nom_entreprise) VALUES
    (3, 'particulier', NULL),
    (4, 'agence', 'AutoPremium');

-- ---------- Voitures ----------
INSERT INTO voitures (id, marque, modele, annee, immatriculation, couleur, nombre_places, transmission, prix_par_jour, statut, owner_id, categorie_id, ville_id) VALUES
    (1, 'Renault', 'Clio 5', 2022, '12345-A-6', 'Rouge', 5, 'Manuel', 250.00, 'DISPONIBLE', 3, 1, 1),
    (2, 'Dacia', 'Sandero', 2021, '23456-B-7', 'Blanc', 5, 'Manuel', 200.00, 'DISPONIBLE', 3, 1, 2),
    (3, 'BMW', 'Série 3', 2023, '34567-C-8', 'Noir', 5, 'Automatique', 500.00, 'DISPONIBLE', 4, 2, 1),
    (4, 'Land Rover', 'Evoque', 2022, '45678-D-9', 'Gris', 5, 'Automatique', 900.00, 'LOUEE', 4, 3, 3),
    (5, 'Toyota', 'Corolla', 2023, '56789-E-1', 'Bleu', 5, 'Automatique', 350.00, 'DISPONIBLE', 3, 2, 4),
    (6, 'Porsche', '911 Carrera', 2021, '67890-F-2', 'Jaune', 2, 'Automatique', 1500.00, 'MAINTENANCE', 4, 4, 3),
    (7, 'Peugeot', '3008', 2022, '78901-G-3', 'Argent', 5, 'Automatique', 400.00, 'DISPONIBLE', 3, 3, 5),
    (8, 'Mercedes', 'Classe C', 2023, '89012-H-4', 'Blanc', 5, 'Automatique', 700.00, 'DISPONIBLE', 4, 6, 6);

-- ---------- Images ----------
INSERT INTO images (id, url, principale, voiture_id) VALUES
    (1, 'https://images.unsplash.com/photo-1449965408869-eaa3f722e40d?w=800', TRUE, 1),
    (2, 'https://images.unsplash.com/photo-1502877338535-766e1452684a?w=800', FALSE, 1),
    (3, 'https://images.unsplash.com/photo-1494976388531-d1058494cdd8?w=800', TRUE, 2),
    (4, 'https://images.unsplash.com/photo-1555215695-3004980ad54e?w=800', TRUE, 3),
    (5, 'https://images.unsplash.com/photo-1606664515524-ed2f786a0bd6?w=800', TRUE, 4),
    (6, 'https://images.unsplash.com/photo-1623869675781-80aa31012a5a?w=800', TRUE, 5),
    (7, 'https://images.unsplash.com/photo-1503736334956-4c8f8e92946d?w=800', TRUE, 6),
    (8, 'https://images.unsplash.com/photo-1511919884226-fd3cad34687c?w=800', TRUE, 7),
    (9, 'https://images.unsplash.com/photo-1618843479313-40f8afb4b4d8?w=800', TRUE, 8);

-- ---------- Réservations ----------
INSERT INTO reservations (id, date_debut, date_fin, montant_total, statut, client_id, voiture_id) VALUES
    (1, '2026-09-20', '2026-09-25', 1750.00, 'CONFIRMEE', 1, 1),
    (2, '2026-10-01', '2026-10-03', 500.00, 'EN_ATTENTE', 2, 2),
    (3, '2026-08-10', '2026-08-15', 2500.00, 'TERMINEE', 1, 3),
    (4, '2026-09-22', '2026-09-24', 800.00, 'ANNULEE', 2, 4),
    (5, '2026-10-05', '2026-10-10', 1750.00, 'EN_ATTENTE', 1, 5);

-- Remise à zéro des compteurs auto_increment
ALTER TABLE utilisateurs AUTO_INCREMENT = 6;
ALTER TABLE voitures AUTO_INCREMENT = 9;
ALTER TABLE images AUTO_INCREMENT = 10;
ALTER TABLE reservations AUTO_INCREMENT = 6;
ALTER TABLE categories AUTO_INCREMENT = 7;
ALTER TABLE villes AUTO_INCREMENT = 7;