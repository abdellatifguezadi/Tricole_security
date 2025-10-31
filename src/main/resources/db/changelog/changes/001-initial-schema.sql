-- liquibase formatted sql

-- changeset lahcen:001-create-fournisseurs-table
CREATE TABLE fournisseurs (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              raison_sociale VARCHAR(255) NOT NULL,
                              adresse VARCHAR(500) NOT NULL,
                              ville VARCHAR(100) NOT NULL,
                              personne_contact VARCHAR(255) NOT NULL,
                              email VARCHAR(255) NOT NULL UNIQUE,
                              telephone VARCHAR(50) NOT NULL,
                              ice VARCHAR(255) NOT NULL UNIQUE,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- changeset lahcen:002-create-produits-table
CREATE TABLE produits (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          reference VARCHAR(50) NOT NULL UNIQUE,
                          nom VARCHAR(255) NOT NULL,
                          description LONGTEXT,
                          prix_unitaire DOUBLE NOT NULL,
                          stock_actuel INT NOT NULL,
                          point_commande INT NOT NULL,
                          unite_mesure VARCHAR(50) NOT NULL,
                          categorie VARCHAR(100) NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- changeset lahcen:003-create-commande-fournisseur-table
CREATE TABLE commande_fournisseur (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      numero_commande VARCHAR(255) NOT NULL UNIQUE,
                                      fournisseur_id BIGINT NOT NULL,
                                      date_commande TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      date_livraison_prevue DATE,
                                      date_livraison_effective DATE,
                                      statut VARCHAR(20) NOT NULL DEFAULT 'EN_ATTENTE',
                                      montant_total DECIMAL(12,2) NOT NULL DEFAULT 0.00,
                                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                      CONSTRAINT fk_commande_fournisseur FOREIGN KEY (fournisseur_id) REFERENCES fournisseurs(id) ON DELETE RESTRICT,
                                      CONSTRAINT chk_statut CHECK (statut IN ('EN_ATTENTE', 'VALIDEE', 'LIVREE', 'ANNULEE'))
);

-- changeset lahcen:004-create-lignes-commande-table
CREATE TABLE lignes_commande (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 commande_id BIGINT NOT NULL,
                                 produit_id BIGINT NOT NULL,
                                 quantite DECIMAL(10,2) NOT NULL,
                                 prix_unitaire DECIMAL(10,2) NOT NULL,
                                 montant_total DECIMAL(12,2) NOT NULL DEFAULT 0.00,
                                 CONSTRAINT fk_ligne_commande FOREIGN KEY (commande_id) REFERENCES commande_fournisseur(id) ON DELETE CASCADE,
                                 CONSTRAINT fk_ligne_produit FOREIGN KEY (produit_id) REFERENCES produits(id) ON DELETE RESTRICT,
                                 CONSTRAINT chk_quantite_positive CHECK (quantite > 0),
                                 CONSTRAINT chk_prix_positif CHECK (prix_unitaire > 0)
);

-- changeset lahcen:005-create-indexes
CREATE INDEX idx_fournisseurs_email ON fournisseurs(email);
CREATE INDEX idx_fournisseurs_ice ON fournisseurs(ice);
CREATE INDEX idx_produits_reference ON produits(reference);
CREATE INDEX idx_produits_categorie ON produits(categorie);
CREATE INDEX idx_commande_fournisseur_id ON commande_fournisseur(fournisseur_id);
CREATE INDEX idx_commande_statut ON commande_fournisseur(statut);
CREATE INDEX idx_commande_date ON commande_fournisseur(date_commande);
CREATE INDEX idx_ligne_commande_id ON lignes_commande(commande_id);
CREATE INDEX idx_ligne_produit_id ON lignes_commande(produit_id);