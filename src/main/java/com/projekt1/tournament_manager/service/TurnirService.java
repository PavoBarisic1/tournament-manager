package com.projekt1.tournament_manager.service;

import com.projekt1.tournament_manager.exception.ResourceNotFoundException;
import com.projekt1.tournament_manager.model.Sport;
import com.projekt1.tournament_manager.model.Turnir;
import com.projekt1.tournament_manager.model.TurnirStatus;
import com.projekt1.tournament_manager.repository.TurnirRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TurnirService {

    private final TurnirRepository repository;

    public List<Turnir> dajSve() {
        return repository.findAll();
    }

    public List<Turnir> dajByStatus(TurnirStatus status) {
        return repository.findByStatus(status);
    }

    public List<Turnir> dajBySport(Sport sport) {
        return repository.findBySport(sport);
    }

    public Turnir dajById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Turnir s id-em " + id + " ne postoji"));
    }

    public Turnir spremi(Turnir turnir) {
        return repository.save(turnir);
    }

    public Turnir azuriraj(Long id, Turnir noviPodaci) {
        Turnir postojeci = dajById(id);
        postojeci.setNaziv(noviPodaci.getNaziv());
        postojeci.setDatum(noviPodaci.getDatum());
        postojeci.setSport(noviPodaci.getSport());
        postojeci.setStatus(noviPodaci.getStatus());
        return repository.save(postojeci);
    }

    public void obrisi(Long id) {
        dajById(id);
        repository.deleteById(id);
    }
}