package org.tricol.supplierchain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tricol.supplierchain.dto.response.BonSortieResponseDTO;
import org.tricol.supplierchain.entity.BonSortie;
import org.tricol.supplierchain.entity.LigneBonSortie;
import org.tricol.supplierchain.entity.LotStock;
import org.tricol.supplierchain.entity.Produit;
import org.tricol.supplierchain.enums.StatutBonSortie;
import org.tricol.supplierchain.mapper.BonSortieMapper;
import org.tricol.supplierchain.repository.BonSortieRepository;
import org.tricol.supplierchain.repository.LotStockRepository;
import org.tricol.supplierchain.repository.MouvementStockRepository;
import org.tricol.supplierchain.repository.ProduitRepository;
import org.tricol.supplierchain.service.inter.BonSortieService;
import org.tricol.supplierchain.service.inter.GestionStockService;
import org.tricol.supplierchain.service.BonSortieServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.tricol.supplierchain.enums.MotifBonSortie.PRODUCTION;


@ExtendWith(MockitoExtension.class)
public class BonSortieServiceTest {

    @InjectMocks
    BonSortieServiceImpl bonSortieService;

    @Mock
    LotStockRepository lotStockRepository;
    @Mock
    BonSortieRepository bonSortieRepository;
    @Mock
    ProduitRepository produitRepository;
    @Mock
    MouvementStockRepository mouvementStockRepository;
    @Mock
    BonSortieMapper bonSortieMapper;
    @Mock
    GestionStockService gestionStockService;

    private Produit produit;
    private BonSortie bonSortie;
    private LigneBonSortie ligne;

    @BeforeEach
    public void setUp() {
        produit = Produit.builder()
                .id(1L)
                .nom("Paracétamol")
                .stockActuel(new BigDecimal("30"))
                .build();

        ligne = LigneBonSortie.builder()
                .produit(produit)
                .quantite(new BigDecimal("10"))
                .build();

        bonSortie = BonSortie.builder()
                .id(1L)
                .numeroBon("BS001")
                .statut(StatutBonSortie.BROUILLON)
                .ligneBonSorties(List.of(ligne))
                .motif(PRODUCTION)
                .build();


    }



    @Test
    @DisplayName("consumation un seul lot")
    public void testConsumationUnSeulLot() {
        LotStock lotStock = LotStock.builder()
                .id(1L)
                .quantiteRestante(new BigDecimal("20"))
                .prixUnitaireAchat(new BigDecimal("5"))
                .dateEntree(LocalDateTime.now().minusDays(5))
                .build();

        when(lotStockRepository.findByProduitIdOrderByDateEntreeAsc(1L))
                .thenReturn(List.of(lotStock));

        when(bonSortieMapper.toResponseDTO(any())).thenReturn(new BonSortieResponseDTO());

        BonSortieResponseDTO responseDTO = bonSortieService.performActualValidation(bonSortie);

        assertThat(responseDTO).isNotNull();
        verify(mouvementStockRepository, times(1)).save(any());
        verify(lotStockRepository,times(1)).save(any());
        verify(bonSortieRepository,times(1)).save(any());

        assertThat(lotStock.getQuantiteRestante()).isEqualTo(new BigDecimal("10"));

    }

}
