package com.projekt1.tournament_manager.repository;

import com.projekt1.tournament_manager.model.Sport;
import com.projekt1.tournament_manager.model.Turnir;
import com.projekt1.tournament_manager.model.TurnirStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurnirRepository extends JpaRepository<Turnir, Long> {

    List<Turnir> findByStatus(TurnirStatus status);
    List<Turnir> findBySport(Sport sport);
}
