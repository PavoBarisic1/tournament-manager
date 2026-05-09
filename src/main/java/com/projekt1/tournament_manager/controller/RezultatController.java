package com.projekt1.tournament_manager.controller;

import com.projekt1.tournament_manager.model.Rezultat;
import com.projekt1.tournament_manager.service.RezultatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/turniri")
@RequiredArgsConstructor
public class RezultatController {

    private final RezultatService service;

    @GetMapping("/{turnirId}/rezultati")
    public List<Rezultat> dajRezultate(@PathVariable Long turnirId) {
        return service.dajRezultateZaTurnir(turnirId);
    }

    @PostMapping("/{turnirId}/rezultati")
    public ResponseEntity<Rezultat> dodajRezultat(
            @PathVariable Long turnirId,
            @RequestParam Long pobjednikId,
            @RequestParam Long gubitnikId,
            @RequestParam(required = false) String opis) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.dodajRezultat(turnirId, pobjednikId, gubitnikId, opis));
    }
}