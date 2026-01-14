package org.tricol.supplierchain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tricol.supplierchain.dto.response.StatistiquesResponseDTO;
import org.tricol.supplierchain.service.inter.StatistiquesService;

@RestController
@RequestMapping("/api/statistiques")
@RequiredArgsConstructor
@Tag(name = "Statistiques", description = "API pour les statistiques du système")
@SecurityRequirement(name = "bearerAuth")
public class StatistiquesController {

    private final StatistiquesService statistiquesService;

    @GetMapping("/dashboard")
    @Operation(
        summary = "Obtenir les statistiques générales",
        description = "Récupère toutes les statistiques importantes pour le tableau de bord : " +
                     "utilisateurs, produits, commandes, stock, finances, alertes, etc."
    )
    @ApiResponse(responseCode = "200", description = "Statistiques récupérées avec succès")
    @PreAuthorize("hasAuthority('STATISTIQUES_READ')")
    public ResponseEntity<StatistiquesResponseDTO> obtenirStatistiquesGenerales() {
        StatistiquesResponseDTO statistiques = statistiquesService.obtenirStatistiquesGenerales();
        return ResponseEntity.ok(statistiques);
    }
}