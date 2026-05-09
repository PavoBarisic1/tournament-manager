package com.projekt1.tournament_manager.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "rezultat")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rezultat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "igraci"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "turnir_id", nullable = false)
    private Turnir turnir;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "turnir"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pobjednik_id", nullable = false)
    private Igrac pobjednik;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "turnir"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gubitnik_id", nullable = false)
    private Igrac gubitnik;

    @Column(nullable = false)
    private LocalDateTime datumOdigravanja;

    @Column
    private String opis;

}
