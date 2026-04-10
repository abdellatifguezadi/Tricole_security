package org.tricol.supplierchain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tricol.supplierchain.entity.LotStock;
import org.tricol.supplierchain.enums.StatutLot;

import java.util.List;
import java.util.Optional;

@Repository
public interface LotStockRepository extends JpaRepository<LotStock, Long> {



    List<LotStock> findByProduitIdAndStatutOrderByDateEntreeAsc(Long produitId, StatutLot statut);

    List<LotStock> findByCommandeFournisseurId(Long commandeId);

    boolean existsByNumeroLot(String numeroLot);

    Long countLotStockByStatut(StatutLot statut);

    List<LotStock> findByStatut(StatutLot statut);

    List<LotStock> findByProduitIdOrderByDateEntreeAsc(Long produitId);

    Optional<LotStock> findTopByProduit_IdOrderByDateEntreeDesc(Long produitId);

}
