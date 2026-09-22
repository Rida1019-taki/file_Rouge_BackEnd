-- Réservations pour l'achat (ACHAT) / la location (LOCATION)
-- Les dates ne sont obligatoires que pour une location.
ALTER TABLE reservations
    ADD COLUMN type VARCHAR(20) NOT NULL DEFAULT 'LOCATION',
    MODIFY COLUMN date_debut DATE NULL,
    MODIFY COLUMN date_fin DATE NULL;