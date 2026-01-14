package org.tricol.supplierchain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tricol.supplierchain.dto.response.StatistiquesResponseDTO;
import org.tricol.supplierchain.entity.*;
import org.tricol.supplierchain.enums.Atelier;
import org.tricol.supplierchain.enums.StatutBonSortie;
import org.tricol.supplierchain.enums.StatutCommande;
import org.tricol.supplierchain.repository.*;
import org.tricol.supplierchain.service.inter.StatistiquesService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatistiquesServiceImpl implements StatistiquesService {

    private final UserRepository userRepository;
    private final ProduitRepository produitRepository;
    private final FournisseurRepository fournisseurRepository;
    private final CommandeFournisseurRepository commandeRepository;
    private final BonSortieRepository bonSortieRepository;
    private final LotStockRepository lotStockRepository;

    @Override
    public StatistiquesResponseDTO obtenirStatistiquesGenerales() {
        return StatistiquesResponseDTO.builder()
                .totalUtilisateurs(userRepository.count())
                .totalProduits(produitRepository.count())
                .totalFournisseurs(fournisseurRepository.count())
                .totalCommandes(commandeRepository.count())
                .totalBonsSortie(bonSortieRepository.count())
                .valeurTotalStock(calculerValeurTotalStock())
                .montantTotalCommandes(calculerMontantTotalCommandes())
                .montantTotalSorties(calculerMontantTotalSorties())
                .commandesParStatut(obtenirCommandesParStatut())
                .bonsSortieParStatut(obtenirBonsSortieParStatut())
                .utilisateursParRole(obtenirUtilisateursParRole())
                .sortiesParAtelier(obtenirSortiesParAtelier())
                .valeursParAtelier(obtenirValeursParAtelier())
                .produitsEnRupture(compterProduitsEnRupture())
                .produitsProchesRupture(compterProduitsProchesRupture())
                .commandesEnRetard(compterCommandesEnRetard())
                .nouvellesCommandesMois(compterNouvellesCommandesMois())
                .nouveauxBonsSortieMois(compterNouveauxBonsSortieMois())
                .mouvementStockMois(calculerMouvementStockMois())
                .build();
    }

    private BigDecimal calculerValeurTotalStock() {
        List<LotStock> lots = lotStockRepository.findAll();
        return lots.stream()
                .map(lot -> lot.getValorisation() != null ? lot.getValorisation() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculerMontantTotalCommandes() {
        List<CommandeFournisseur> commandes = commandeRepository.findAll();
        return commandes.stream()
                .map(cmd -> cmd.getMontantTotal() != null ? cmd.getMontantTotal() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculerMontantTotalSorties() {
        List<BonSortie> bonsSortie = bonSortieRepository.findAll();
        return bonsSortie.stream()
                .map(bon -> bon.getMontantTotal() != null ? bon.getMontantTotal() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Map<String, Long> obtenirCommandesParStatut() {
        List<CommandeFournisseur> commandes = commandeRepository.findAll();
        return commandes.stream()
                .collect(Collectors.groupingBy(
                        cmd -> cmd.getStatut().name(),
                        Collectors.counting()
                ));
    }

    private Map<String, Long> obtenirBonsSortieParStatut() {
        List<BonSortie> bonsSortie = bonSortieRepository.findAll();
        return bonsSortie.stream()
                .collect(Collectors.groupingBy(
                        bon -> bon.getStatut().name(),
                        Collectors.counting()
                ));
    }

    private Map<String, Long> obtenirUtilisateursParRole() {
        List<UserApp> users = userRepository.findAll();
        return users.stream()
                .filter(user -> user.getRole() != null)
                .collect(Collectors.groupingBy(
                        user -> user.getRole().getName().name(),
                        Collectors.counting()
                ));
    }

    private Map<String, Long> obtenirSortiesParAtelier() {
        List<BonSortie> bonsSortie = bonSortieRepository.findAll();
        return bonsSortie.stream()
                .filter(bon -> bon.getAtelier() != null)
                .collect(Collectors.groupingBy(
                        bon -> bon.getAtelier().name(),
                        Collectors.counting()
                ));
    }

    private Map<String, BigDecimal> obtenirValeursParAtelier() {
        List<BonSortie> bonsSortie = bonSortieRepository.findAll();
        return bonsSortie.stream()
                .filter(bon -> bon.getAtelier() != null)
                .collect(Collectors.groupingBy(
                        bon -> bon.getAtelier().name(),
                        Collectors.reducing(BigDecimal.ZERO, 
                            bon -> bon.getMontantTotal() != null ? bon.getMontantTotal() : BigDecimal.ZERO, 
                            BigDecimal::add)
                ));
    }

    private Long compterProduitsEnRupture() {
        List<Produit> produits = produitRepository.findAll();
        return produits.stream()
                .filter(p -> p.getStockActuel().compareTo(BigDecimal.ZERO) == 0)
                .count();
    }

    private Long compterProduitsProchesRupture() {
        List<Produit> produits = produitRepository.findAll();
        return produits.stream()
                .filter(p -> p.getStockActuel().compareTo(p.getPointCommande()) <= 0 
                        && p.getStockActuel().compareTo(BigDecimal.ZERO) > 0)
                .count();
    }

    private Long compterCommandesEnRetard() {
        LocalDateTime maintenant = LocalDateTime.now();
        List<CommandeFournisseur> commandes = commandeRepository.findAll();
        return commandes.stream()
                .filter(cmd -> cmd.getStatut() == StatutCommande.EN_ATTENTE 
                        && cmd.getDateLivraisonPrevue() != null
                        && cmd.getDateLivraisonPrevue().isBefore(maintenant.toLocalDate()))
                .count();
    }

    private Long compterNouvellesCommandesMois() {
        LocalDateTime debutMois = LocalDateTime.now().minusDays(30);
        List<CommandeFournisseur> commandes = commandeRepository.findAll();
        return commandes.stream()
                .filter(cmd -> cmd.getDateCommande().isAfter(debutMois))
                .count();
    }

    private Long compterNouveauxBonsSortieMois() {
        LocalDateTime debutMois = LocalDateTime.now().minusDays(30);
        List<BonSortie> bonsSortie = bonSortieRepository.findAll();
        return bonsSortie.stream()
                .filter(bon -> bon.getDateCreation().isAfter(debutMois))
                .count();
    }

    private BigDecimal calculerMouvementStockMois() {
        LocalDateTime debutMois = LocalDateTime.now().minusDays(30);
        List<BonSortie> bonsSortie = bonSortieRepository.findAll();
        return bonsSortie.stream()
                .filter(bon -> bon.getDateCreation().isAfter(debutMois))
                .map(bon -> bon.getMontantTotal() != null ? bon.getMontantTotal() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}