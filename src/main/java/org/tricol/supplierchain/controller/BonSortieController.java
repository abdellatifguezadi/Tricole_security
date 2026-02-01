package org.tricol.supplierchain.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.tricol.supplierchain.dto.request.BonSortieRequestDTO;
import org.tricol.supplierchain.dto.request.BonSortieUpdateDTO;
import org.tricol.supplierchain.dto.response.BonSortieResponseDTO;
import org.tricol.supplierchain.enums.Atelier;
import org.tricol.supplierchain.service.inter.BonSortieService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bonSorties")
public class BonSortieController {

    private final BonSortieService bonSortieService;

    @PostMapping()
    @PreAuthorize("hasAuthority('BON_SORTIE_CREATE')")
    public ResponseEntity<BonSortieResponseDTO> createBonSortie(@RequestBody @Valid BonSortieRequestDTO bonSortieRequestDTO) {

        BonSortieResponseDTO responseDTO = bonSortieService.createBonSortie(bonSortieRequestDTO);
        return ResponseEntity.ok(responseDTO);

    }


    @GetMapping()
    @PreAuthorize("hasAuthority('BON_SORTIE_READ')")
    public ResponseEntity<List<BonSortieResponseDTO>> getBonSorties() {
        List<BonSortieResponseDTO> bonSorties = bonSortieService.getBonSorties();
        return ResponseEntity.ok(bonSorties);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('BON_SORTIE_READ')")
    public ResponseEntity<BonSortieResponseDTO> getBonSortieById(@PathVariable Long id) {
        BonSortieResponseDTO bonSortie = bonSortieService.getBonSortieById(id);
        return ResponseEntity.ok(bonSortie);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('BON_SORTIE_DELETE')")
    public ResponseEntity<Map<String, String>> deleteBonSortie(@PathVariable Long id) {
        bonSortieService.deleteBonSortie(id);
        Map<String, String> body = new HashMap<>();
        body.put("message", "Bon de sortie avec id " + id + " est supprimé");
        return ResponseEntity.ok(body);
    }

    @GetMapping("/atelier/{atelier}")
    @PreAuthorize("hasAuthority('BON_SORTIE_READ')")
    public ResponseEntity<List<BonSortieResponseDTO>> getBonSortiesByAtelier(@PathVariable Atelier atelier) {
        List<BonSortieResponseDTO> bonSorties = bonSortieService.getBonSortiesByAtelier(atelier);
        return ResponseEntity.ok(bonSorties);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('BON_SORTIE_UPDATE')")
    public ResponseEntity<BonSortieResponseDTO> updateBonSortie(@PathVariable Long id, @Valid @RequestBody BonSortieUpdateDTO bonSortieUpdateDTO) {
        BonSortieResponseDTO updatedBonSortie = bonSortieService.updateBonSortie(id, bonSortieUpdateDTO);
        return ResponseEntity.ok(updatedBonSortie);
    }


    @PutMapping("/annulation/{id}")
    @PreAuthorize("hasAuthority('BON_SORTIE_CANCEL')")
    public ResponseEntity<Map<String, String>> annulationBonSortie(@PathVariable Long id) {
        bonSortieService.annulationBonSortie(id);
        Map<String, String> body = new HashMap<>();
        body.put("message", "Bon de sortie avec id " + id + " est annulé");
        return ResponseEntity.ok(body);
    }

    @PutMapping("/validation/{id}")
    @PreAuthorize("hasAuthority('BON_SORTIE_VALIDATE')")
    public ResponseEntity<BonSortieResponseDTO> validationBonSortie(@PathVariable Long id) {
            BonSortieResponseDTO validatedBonSortie = bonSortieService.validationBonSortie(id);
            return ResponseEntity.ok(validatedBonSortie);
        }

}
