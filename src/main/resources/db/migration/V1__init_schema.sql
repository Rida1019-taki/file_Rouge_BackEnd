CREATE TABLE utilisateurs (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              nom VARCHAR(255) NOT NULL,
                              prenom VARCHAR(255) NOT NULL,
                              email VARCHAR(255) NOT NULL UNIQUE,
                              telephone VARCHAR(50) NOT NULL,
                              mot_de_passe VARCHAR(255) NOT NULL,
                              role VARCHAR(50) NOT NULL,
                              actif BOOLEAN DEFAULT TRUE
);

CREATE TABLE clients (
                         id BIGINT PRIMARY KEY,
                         numero_permis VARCHAR(100),
                         date_obtention_permis DATE,
                         CONSTRAINT fk_clients_utilisateurs FOREIGN KEY (id) REFERENCES utilisateurs(id) ON DELETE CASCADE
);

CREATE TABLE owners (
                        id BIGINT PRIMARY KEY,
                        type_owner ENUM('particulier', 'agence'),
                        nom_entreprise VARCHAR(255),
                        CONSTRAINT fk_owners_utilisateurs FOREIGN KEY (id) REFERENCES utilisateurs(id) ON DELETE CASCADE
);

CREATE TABLE categories (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            nom VARCHAR(255) NOT NULL UNIQUE,
                            description TEXT
);

CREATE TABLE villes (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        nom VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE voitures (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          marque VARCHAR(255) NOT NULL,
                          modele VARCHAR(255) NOT NULL,
                          annee INT,
                          immatriculation VARCHAR(100),
                          couleur VARCHAR(50),
                          nombre_places INT,
                          transmission VARCHAR(50),
                          prix_par_jour DECIMAL(10, 2),
                          statut VARCHAR(50) DEFAULT 'DISPONIBLE',
                          owner_id BIGINT NOT NULL,
                          categorie_id BIGINT NOT NULL,
                          ville_id BIGINT NOT NULL,
                          CONSTRAINT fk_voitures_owners FOREIGN KEY (owner_id) REFERENCES owners(id) ON DELETE CASCADE,
                          CONSTRAINT fk_voitures_categories FOREIGN KEY (categorie_id) REFERENCES categories(id) ON DELETE RESTRICT,
                          CONSTRAINT fk_voitures_villes FOREIGN KEY (ville_id) REFERENCES villes(id) ON DELETE RESTRICT
);

CREATE TABLE images (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        url VARCHAR(500) NOT NULL,
                        principale BOOLEAN DEFAULT FALSE,
                        voiture_id BIGINT NOT NULL,
                        CONSTRAINT fk_images_voitures FOREIGN KEY (voiture_id) REFERENCES voitures(id) ON DELETE CASCADE
);

CREATE TABLE reservations (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              date_debut DATE NOT NULL,
                              date_fin DATE NOT NULL,
                              montant_total DECIMAL(10, 2),
                              statut VARCHAR(50) DEFAULT 'EN_ATTENTE',
                              client_id BIGINT NOT NULL,
                              voiture_id BIGINT NOT NULL,
                              CONSTRAINT fk_reservations_clients FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE,
                              CONSTRAINT fk_reservations_voitures FOREIGN KEY (voiture_id) REFERENCES voitures(id) ON DELETE CASCADE
);