package org.tricol.supplierchain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tricol.supplierchain.entity.LigneCommande;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface LigneCommandeRepository extends JpaRepository<LigneCommande, Long> {
    boolean existsByProduitId(Long produitId);

    @Query(value = """
            SELECT l.prix_unitaire FROM lignes_commande l
            INNER JOIN commande_fournisseur c ON l.commande_id = c.id
            WHERE l.produit_id = :produitId
            ORDER BY c.date_commande DESC, l.id DESC
            LIMIT 1
            """, nativeQuery = true)
    Optional<BigDecimal> findDernierPrixUnitaireByProduitId(@Param("produitId") Long produitId);

    @Query(value = """
            SELECT l.prix_unitaire FROM lignes_commande l
            INNER JOIN commande_fournisseur c ON l.commande_id = c.id
            WHERE l.produit_id = :produitId AND c.fournisseur_id = :fournisseurId
            ORDER BY c.date_commande DESC, l.id DESC
            LIMIT 1
            """, nativeQuery = true)
    Optional<BigDecimal> findDernierPrixAchat(@Param("produitId") Long produitId, @Param("fournisseurId") Long fournisseurId);

}
