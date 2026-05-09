package com.projekt1.tournament_manager.repository;

import com.projekt1.tournament_manager.model.Igrac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IgracRepository extends JpaRepository<Igrac, Long> {

    List<Igrac> findByTurnirId(Long turnirId);
    List<Igrac> findByTurnirIdOrderByBodoviDesc(Long turnirId);

}
