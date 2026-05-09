package com.projekt1.tournament_manager.service;

import com.projekt1.tournament_manager.exception.ResourceNotFoundException;
import com.projekt1.tournament_manager.model.Igrac;
import com.projekt1.tournament_manager.model.Rezultat;
import com.projekt1.tournament_manager.model.Turnir;
import com.projekt1.tournament_manager.repository.IgracRepository;
import com.projekt1.tournament_manager.repository.RezultatRepository;
import com.projekt1.tournament_manager.repository.TurnirRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RezultatService {

    private final RezultatRepository rezultatRepository;
    private final TurnirRepository turnirRepository;
    private final IgracRepository igracRepository;

    public List<Rezultat> dajRezultateZaTurnir(Long turnirId) {
        if (!turnirRepository.existsById(turnirId)) {
            throw new ResourceNotFoundException(
                    "Turnir s id-em " + turnirId + " ne postoji");
        }
        return rezultatRepository.findByTurnirId(turnirId);
    }

    public Rezultat dodajRezultat(Long turnirId, Long pobjednikId, Long gubitnikId, String opis) {
        Turnir turnir = turnirRepository.findById(turnirId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Turnir s id-em " + turnirId + " ne postoji"));

        Igrac pobjednik = igracRepository.findById(pobjednikId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Igrac s id-em " + pobjednikId + " ne postoji"));

        Igrac gubitnik = igracRepository.findById(gubitnikId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Igrac s id-em " + gubitnikId + " ne postoji"));

        pobjednik.setBodovi(pobjednik.getBodovi() + 3);
        igracRepository.save(pobjednik);

        Rezultat rezultat = Rezultat.builder()
                .turnir(turnir)
                .pobjednik(pobjednik)
                .gubitnik(gubitnik)
                .datumOdigravanja(java.time.LocalDateTime.now())
                .opis(opis)
                .build();

        return rezultatRepository.save(rezultat);
    }
}