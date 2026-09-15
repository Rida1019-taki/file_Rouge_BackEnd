-- =============================================================
-- Tomobilty.ma - Données marketplace (Vente + Location)
-- Ajoute des données réalistes et variées pour tester les deux
-- activités de la plateforme.
--
-- Règles respectées :
--   - Aucune modification d'architecture / de schéma.
--   - Aucune suppression de données existantes.
--   - Réutilisation des catégories, villes, owners/clients existants.
--   - listing_type = 'SALE' pour la vente, 'RENTAL' pour la location
--     (jamais le champ statut pour distinguer les deux).
--   - Mots de passe BCrypt (123456) identiques aux comptes de test V2.
--   - Transmission : valeurs compatibles formulaire/filtré frontend
--     (MANUELLE / AUTOMATIQUE).
--   - Aucune réservation pour les voitures destinées à la vente.
-- =============================================================

-- ---------- Catégorie supplémentaire (existantes réutilisées) ----------
INSERT INTO categories (id, nom, description) VALUES
    (7, 'UTILITAIRE', 'Véhicules utilitaires et fourgons');

-- ---------- Villes marocaines supplémentaires ----------
INSERT INTO villes (id, nom) VALUES
    (7, 'Oued Zem'),
    (8, 'Khouribga'),
    (9, 'Beni Mellal'),
    (10, 'El Jadida'),
    (11, 'Meknès'),
    (12, 'Oujda'),
    (13, 'Kénitra'),
    (14, 'Mohammedia');

-- ---------- Utilisateurs / Owners / Client (test) ----------
-- id 6-9 : Owners (2 particuliers + 2 agences), id 10 : Client
-- Mot de passe BCrypt : 123456 (hash identique aux comptes V2)
INSERT INTO utilisateurs (id, nom, prenom, email, telephone, mot_de_passe, role, actif) VALUES
    (6, 'Bennani', 'Omar', 'omar.bennani@test.com', '0666666666', '$2a$10$QFYddcdNGYyRloH9AXP55.Sdq6oIhzACYv8.WVCsQEAOZIkj8VUpe', 'OWNER', TRUE),
    (7, 'Berrada', 'Sara', 'sara.berrada@test.com', '0677777777', '$2a$10$QFYddcdNGYyRloH9AXP55.Sdq6oIhzACYv8.WVCsQEAOZIkj8VUpe', 'OWNER', TRUE),
    (8, 'Bouskri', 'Mehdi', 'contact@atlascars.ma', '0688888888', '$2a$10$QFYddcdNGYyRloH9AXP55.Sdq6oIhzACYv8.WVCsQEAOZIkj8VUpe', 'OWNER', TRUE),
    (9, 'Ouazzani', 'Imane', 'contact@premiumlocation.ma', '0699999999', '$2a$10$QFYddcdNGYyRloH9AXP55.Sdq6oIhzACYv8.WVCsQEAOZIkj8VUpe', 'OWNER', TRUE),
    (10, 'Lamrani', 'Youssef', 'youssef.lamrani@test.com', '0610101010', '$2a$10$QFYddcdNGYyRloH9AXP55.Sdq6oIhzACYv8.WVCsQEAOZIkj8VUpe', 'CLIENT', TRUE);

INSERT INTO owners (id, type_owner, nom_entreprise) VALUES
    (6, 'particulier', NULL),              -- Owner 1
    (7, 'particulier', NULL),              -- Owner 2
    (8, 'agence', 'Atlas Cars'),           -- Agence 1
    (9, 'agence', 'Premium Location');     -- Agence 2

INSERT INTO clients (id) VALUES (10);

-- =============================================================
-- VOITURES À VENDRE (SALE) - 13 nouvelles
-- Statut : DISPONIBLE = à vendre ; INACTIVE = retirée de la vente
-- =============================================================
INSERT INTO voitures (id, marque, modele, annee, immatriculation, couleur, nombre_places, transmission, prix_par_jour, prix_vente, listing_type, statut, owner_id, categorie_id, ville_id) VALUES
    (9,  'Dacia',      'Logan',            2022, '9999-A-10', 'Argent',  5, 'MANUELLE',     NULL,  95000.00, 'SALE', 'DISPONIBLE', 6, 1, 7),
    (10, 'Renault',    'Clio 5',           2023, '9999-B-11', 'Blanc',   5, 'AUTOMATIQUE', NULL, 145000.00, 'SALE', 'DISPONIBLE', 7, 1, 9),
    (11, 'Peugeot',    '208',              2021, '9999-C-12', 'Noir',    5, 'MANUELLE',    NULL, 125000.00, 'SALE', 'DISPONIBLE', 6, 1, 8),
    (12, 'Toyota',     'Corolla',          2021, '9999-D-13', 'Gris',    5, 'AUTOMATIQUE', NULL, 175000.00, 'SALE', 'DISPONIBLE', 7, 2, 1),
    (13, 'Volkswagen', 'Golf 7',           2020, '9999-E-14', 'Bleu',    5, 'MANUELLE',    NULL, 165000.00, 'SALE', 'DISPONIBLE', 8, 2, 2),
    (14, 'Hyundai',    'Tucson',           2022, '9999-F-15', 'Blanc',   5, 'AUTOMATIQUE', NULL, 245000.00, 'SALE', 'DISPONIBLE', 8, 3, 3),
    (15, 'Kia',        'Sportage',         2023, '9999-G-16', 'Rouge',   5, 'AUTOMATIQUE', NULL, 268000.00, 'SALE', 'DISPONIBLE', 9, 3, 6),
    (16, 'Renault',    'Duster',           2021, '9999-H-17', 'Orange',  5, 'MANUELLE',    NULL, 142000.00, 'SALE', 'DISPONIBLE', 9, 3, 4),
    (17, 'Fiat',       'Doblo',            2022, '9999-I-18', 'Blanc',   5, 'MANUELLE',    NULL, 138000.00, 'SALE', 'DISPONIBLE', 8, 7, 5),
    (18, 'Audi',       'A4',               2019, '9999-J-19', 'Noir',    5, 'AUTOMATIQUE', NULL, 289000.00, 'SALE', 'INACTIVE',   7, 2, 1),
    (19, 'Ford',       'Transit Connect',  2020, '9999-K-20', 'Argent',  5, 'MANUELLE',    NULL, 178000.00, 'SALE', 'DISPONIBLE', 6, 7, 12),
    (20, 'BMW',        'X3',               2021, '9999-L-21', 'Bleu',    5, 'AUTOMATIQUE', NULL, 385000.00, 'SALE', 'DISPONIBLE', 9, 3, 1),
    (21, 'Peugeot',    '508',              2022, '9999-M-22', 'Gris',    5, 'AUTOMATIQUE', NULL, 255000.00, 'SALE', 'DISPONIBLE', 8, 2, 13);

-- =============================================================
-- VOITURES À LOUER (RENTAL) - 13 nouvelles
-- Statuts : DISPONIBLE / LOUEE / MAINTENANCE / INACTIVE
-- =============================================================
INSERT INTO voitures (id, marque, modele, annee, immatriculation, couleur, nombre_places, transmission, prix_par_jour, prix_vente, listing_type, statut, owner_id, categorie_id, ville_id) VALUES
    (22, 'Dacia',      'Sandero',     2023, '9999-N-23', 'Blanc',   5, 'MANUELLE',     350.00, NULL, 'RENTAL', 'DISPONIBLE',  6, 1, 7),
    (23, 'Hyundai',    'Tucson',      2024, '9999-O-24', 'Gris',    5, 'AUTOMATIQUE',  650.00, NULL, 'RENTAL', 'DISPONIBLE',  9, 3, 9),
    (24, 'Renault',    'Clio',        2022, '9999-P-25', 'Noir',    5, 'MANUELLE',     400.00, NULL, 'RENTAL', 'LOUEE',       6, 1, 8),
    (25, 'Volkswagen', 'Polo',        2022, '9999-Q-26', 'Bleu',    5, 'MANUELLE',     380.00, NULL, 'RENTAL', 'DISPONIBLE',  7, 1, 1),
    (26, 'Kia',        'Picanto',     2021, '9999-R-27', 'Rouge',   4, 'MANUELLE',     220.00, NULL, 'RENTAL', 'DISPONIBLE',  7, 1, 3),
    (27, 'Dacia',      'Jogger',      2023, '9999-S-28', 'Argent',  7, 'MANUELLE',     450.00, NULL, 'RENTAL', 'DISPONIBLE',  8, 5, 5),
    (28, 'Peugeot',    '3008',        2023, '9999-T-29', 'Blanc',   5, 'AUTOMATIQUE',  600.00, NULL, 'RENTAL', 'LOUEE',       8, 3, 2),
    (29, 'Toyota',     'RAV4',        2022, '9999-U-30', 'Vert',    5, 'AUTOMATIQUE',  750.00, NULL, 'RENTAL', 'MAINTENANCE', 9, 3, 6),
    (30, 'Ford',       'Focus',       2021, '9999-V-31', 'Gris',    5, 'AUTOMATIQUE',  420.00, NULL, 'RENTAL', 'DISPONIBLE',  6, 2, 10),
    (31, 'Renault',    'Captur',      2023, '9999-W-32', 'Orange',  5, 'MANUELLE',     480.00, NULL, 'RENTAL', 'DISPONIBLE',  7, 1, 11),
    (32, 'Peugeot',    'Partner',     2020, '9999-X-33', 'Blanc',   5, 'MANUELLE',     380.00, NULL, 'RENTAL', 'DISPONIBLE',  8, 7, 4),
    (33, 'Mercedes',   'Classe A',    2022, '9999-Y-34', 'Blanc',   5, 'AUTOMATIQUE',  850.00, NULL, 'RENTAL', 'DISPONIBLE',  9, 6, 1),
    (34, 'Seat',       'Ibiza',       2020, '9999-Z-35', 'Argent',  5, 'MANUELLE',     300.00, NULL, 'RENTAL', 'INACTIVE',    6, 1, 14);

-- =============================================================
-- IMAGES (au moins une par voiture ; quelques secondaires)
-- =============================================================
INSERT INTO images (id, url, principale, voiture_id) VALUES
    (10, 'https://images.unsplash.com/photo-1502877338535-766e1452684a?w=800', TRUE, 9),
    (11, 'https://images.unsplash.com/photo-1449965408869-eaa3f722e40d?w=800', FALSE, 9),
    (12, 'https://images.unsplash.com/photo-1494976388531-d1058494cdd8?w=800', TRUE, 10),
    (13, 'https://images.unsplash.com/photo-1549317661-bd32c8ce0db2?w=800', FALSE, 10),
    (14, 'https://images.unsplash.com/photo-1583121274602-3e2820c69888?w=800', TRUE, 11),
    (15, 'https://images.unsplash.com/photo-1623869675781-80aa31012a5a?w=800', TRUE, 12),
    (16, 'https://images.unsplash.com/photo-1449965408869-eaa3f722e40d?w=800', TRUE, 13),
    (17, 'https://images.unsplash.com/photo-1553440569-bcc63803a83d?w=800', TRUE, 14),
    (18, 'https://images.unsplash.com/photo-1606664515524-ed2f786a0bd6?w=800', TRUE, 15),
    (19, 'https://images.unsplash.com/photo-1511919884226-fd3cad34687c?w=800', TRUE, 16),
    (20, 'https://images.unsplash.com/photo-1618843479313-40f8afb4b4d8?w=800', TRUE, 17),
    (21, 'https://images.unsplash.com/photo-1555215695-3004980ad54e?w=800', TRUE, 18),
    (22, 'https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=800', TRUE, 19),
    (23, 'https://images.unsplash.com/photo-1549317661-bd32c8ce0db2?w=800', TRUE, 20),
    (24, 'https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=800', TRUE, 21),
    (25, 'https://images.unsplash.com/photo-1494976388531-d1058494cdd8?w=800', TRUE, 22),
    (26, 'https://images.unsplash.com/photo-1583121274602-3e2820c69888?w=800', FALSE, 22),
    (27, 'https://images.unsplash.com/photo-1553440569-bcc63803a83d?w=800', TRUE, 23),
    (28, 'https://images.unsplash.com/photo-1606664515524-ed2f786a0bd6?w=800', FALSE, 23),
    (29, 'https://images.unsplash.com/photo-1449965408869-eaa3f722e40d?w=800', TRUE, 24),
    (30, 'https://images.unsplash.com/photo-1503736334956-4c8f8e92946d?w=800', TRUE, 25),
    (31, 'https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=800', TRUE, 26),
    (32, 'https://images.unsplash.com/photo-1583121274602-3e2820c69888?w=800', TRUE, 27),
    (33, 'https://images.unsplash.com/photo-1511919884226-fd3cad34687c?w=800', TRUE, 28),
    (34, 'https://images.unsplash.com/photo-1606664515524-ed2f786a0bd6?w=800', TRUE, 29),
    (35, 'https://images.unsplash.com/photo-1623869675781-80aa31012a5a?w=800', TRUE, 30),
    (36, 'https://images.unsplash.com/photo-1494976388531-d1058494cdd8?w=800', TRUE, 31),
    (37, 'https://images.unsplash.com/photo-1618843479313-40f8afb4b4d8?w=800', TRUE, 32),
    (38, 'https://images.unsplash.com/photo-1555215695-3004980ad54e?w=800', TRUE, 33),
    (39, 'https://images.unsplash.com/photo-1502877338535-766e1452684a?w=800', TRUE, 34);

-- =============================================================
-- RÉSERVATIONS DE TEST (uniquement pour des voitures à LOUER)
-- montant_total = prix_par_jour x nombre de jours
-- =============================================================
INSERT INTO reservations (id, date_debut, date_fin, montant_total, statut, client_id, voiture_id) VALUES
    (6, '2026-10-12', '2026-10-14', 700.00,  'CONFIRMEE', 2, 22),  -- Sandero 350 x 2
    (7, '2026-11-01', '2026-11-03', 1300.00, 'EN_ATTENTE', 10, 23), -- Tucson 650 x 2
    (8, '2026-09-25', '2026-09-27', 800.00,  'CONFIRMEE', 1, 24),  -- Clio 400 x 2
    (9, '2026-08-20', '2026-08-23', 1800.00, 'TERMINEE', 10, 28),  -- 3008 600 x 3
    (10, '2026-12-01', '2026-12-05', 1520.00, 'EN_ATTENTE', 2, 25); -- Polo 380 x 4

-- Remise à zéro des compteurs auto_increment
ALTER TABLE utilisateurs AUTO_INCREMENT = 11;
ALTER TABLE voitures AUTO_INCREMENT = 35;
ALTER TABLE images AUTO_INCREMENT = 40;
ALTER TABLE reservations AUTO_INCREMENT = 11;
ALTER TABLE categories AUTO_INCREMENT = 8;
ALTER TABLE villes AUTO_INCREMENT = 15;