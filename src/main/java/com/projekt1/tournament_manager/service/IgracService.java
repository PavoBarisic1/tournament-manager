package com.projekt1.tournament_manager.service;

import com.projekt1.tournament_manager.exception.ResourceNotFoundException;
import com.projekt1.tournament_manager.model.Igrac;
import com.projekt1.tournament_manager.model.Turnir;
import com.projekt1.tournament_manager.repository.IgracRepository;
import com.projekt1.tournament_manager.repository.TurnirRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IgracService {

    private final IgracRepository igracRepository;
    private final TurnirRepository turnirRepository;

    public List<Igrac> dajIgraceZaTurnir(Long turnirId) {
        if (!turnirRepository.existsById(turnirId)) {
            throw new ResourceNotFoundException(
                    "Turnir s id-em " + turnirId + " ne postoji");
        }
        return igracRepository.findByTurnirId(turnirId);
    }

    public List<Igrac> dajRangListu(Long turnirId) {
        if (!turnirRepository.existsById(turnirId)) {
            throw new ResourceNotFoundException(
                    "Turnir s id-em " + turnirId + " ne postoji");
        }
        return igracRepository.findByTurnirIdOrderByBodoviDesc(turnirId);
    }

    public Igrac dodajIgraca(Long turnirId, Igrac igrac) {
        Turnir turnir = turnirRepository.findById(turnirId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Turnir s id-em " + turnirId + " ne postoji"));
        igrac.setTurnir(turnir);
        return igracRepository.save(igrac);
    }

    public Igrac dajById(Long id) {
        return igracRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Igrac s id-em " + id + " ne postoji"));
    }

    public void obrisi(Long id) {
        dajById(id);
        igracRepository.deleteById(id);
    }
}