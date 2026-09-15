-- =============================================================
-- Tomobilty.ma - Séparation Vente / Location
-- Ajoute le type d'annonce (listing_type) et le prix de vente.
-- Les voitures existantes étaient toutes destinées à la location
-- (elles possédaient prix_par_jour et des réservations associées),
-- elles sont donc migrées par défaut vers RENTAL.
-- Quelques véhicules sont réaffectés à la vente pour la démo.
-- =============================================================

ALTER TABLE voitures ADD COLUMN listing_type VARCHAR(20) NOT NULL DEFAULT 'RENTAL' AFTER statut;
ALTER TABLE voitures ADD COLUMN prix_vente DECIMAL(10, 2) NULL AFTER prix_par_jour;

UPDATE voitures SET listing_type = 'SALE', prix_vente = 320000.00 WHERE id = 3;
UPDATE voitures SET listing_type = 'SALE', prix_vente = 148000.00 WHERE id = 7;
UPDATE voitures SET listing_type = 'SALE', prix_vente = 480000.00 WHERE id = 8;