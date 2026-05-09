package com.projekt1.tournament_manager.controller;

import com.projekt1.tournament_manager.model.Igrac;
import com.projekt1.tournament_manager.service.IgracService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/turniri")
@RequiredArgsConstructor
public class IgracController {

    private final IgracService service;

    @GetMapping("/{turnirId}/igraci")
    public List<Igrac> dajIgrace(@PathVariable Long turnirId) {
        return service.dajIgraceZaTurnir(turnirId);
    }

    @GetMapping("/{turnirId}/rang-lista")
    public List<Igrac> dajRangListu(@PathVariable Long turnirId) {
        return service.dajRangListu(turnirId);
    }

    @PostMapping("/{turnirId}/igraci")
    public ResponseEntity<Igrac> dodajIgraca(@PathVariable Long turnirId,
                                             @Valid @RequestBody Igrac igrac) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.dodajIgraca(turnirId, igrac));
    }

    @DeleteMapping("/igraci/{id}")
    public ResponseEntity<Void> obrisiIgraca(@PathVariable Long id) {
        service.obrisi(id);
        return ResponseEntity.noContent().build();
    }
}