package org.tricol.supplierchain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatistiquesResponseDTO {
    
    // Statistiques générales
    private Long totalUtilisateurs;
    private Long totalProduits;
    private Long totalFournisseurs;
    private Long totalCommandes;
    private Long totalBonsSortie;
    
    // Statistiques financières
    private BigDecimal valeurTotalStock;
    private BigDecimal montantTotalCommandes;
    private BigDecimal montantTotalSorties;
    
    // Statistiques par statut
    private Map<String, Long> commandesParStatut;
    private Map<String, Long> bonsSortieParStatut;
    private Map<String, Long> utilisateursParRole;
    
    // Statistiques par atelier
    private Map<String, Long> sortiesParAtelier;
    private Map<String, BigDecimal> valeursParAtelier;
    
    // Alertes et indicateurs
    private Long produitsEnRupture;
    private Long produitsProchesRupture;
    private Long commandesEnRetard;
    
    // Statistiques temporelles (derniers 30 jours)
    private Long nouvellesCommandesMois;
    private Long nouveauxBonsSortieMois;
    private BigDecimal mouvementStockMois;
}