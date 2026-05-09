package com.projekt1.tournament_manager.controller;

import com.projekt1.tournament_manager.model.Sport;
import com.projekt1.tournament_manager.model.Turnir;
import com.projekt1.tournament_manager.model.TurnirStatus;
import com.projekt1.tournament_manager.service.TurnirService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/turniri")
@RequiredArgsConstructor
public class TurnirController {

    private final TurnirService service;

    @GetMapping
    public List<Turnir> dajSve(
            @RequestParam(required = false) TurnirStatus status,
            @RequestParam(required = false) Sport sport) {
        if (status != null && sport != null) {
            return service.dajByStatus(status).stream()
                    .filter(t -> t.getSport() == sport)
                    .toList();
        } else if (status != null) {
            return service.dajByStatus(status);
        } else if (sport != null) {
            return service.dajBySport(sport);
        }
        return service.dajSve();
    }

    @GetMapping("/{id}")
    public Turnir dajById(@PathVariable Long id) {
        return service.dajById(id);
    }

    @PostMapping
    public ResponseEntity<Turnir> dodaj(@Valid @RequestBody Turnir turnir) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.spremi(turnir));
    }

    @PutMapping("/{id}")
    public Turnir azuriraj(@PathVariable Long id, @Valid @RequestBody Turnir turnir) {
        return service.azuriraj(id, turnir);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> obrisi(@PathVariable Long id) {
        service.obrisi(id);
        return ResponseEntity.noContent().build();
    }
}