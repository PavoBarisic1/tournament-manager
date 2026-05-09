package com.projekt1.tournament_manager.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "igrac")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Igrac {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Ime ne smije biti prazno!")
    @Column(nullable = false)
    private String ime;

    @NotNull(message = "Prezime ne smije biti prazno!")
    @Column(nullable = false)
    private String prezime;

    @Column
    private String pozicija;

    @Column(nullable = false)
    @Builder.Default
    private int bodovi = 0;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "igraci"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "turnir_id", nullable = false)
    private Turnir turnir;
}
