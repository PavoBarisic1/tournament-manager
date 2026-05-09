package com.projekt1.tournament_manager.repository;

import com.projekt1.tournament_manager.model.Rezultat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RezultatRepository extends JpaRepository<Rezultat, Long> {

    List<Rezultat> findByTurnirId(Long turnirId);

}
