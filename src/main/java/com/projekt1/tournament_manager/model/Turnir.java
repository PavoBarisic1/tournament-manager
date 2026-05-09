package com.projekt1.tournament_manager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "turnir")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Turnir {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Naziv ne smije biti prazan!")
    @Column(nullable = false)
    private String naziv;

    @NotNull(message = "Datum je obavezan!")
    @Column(nullable = false)
    private LocalDateTime datum;

    @NotNull(message = "Sport je obavezan!")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sport sport;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private TurnirStatus status = TurnirStatus.UPCOMING;

    @JsonIgnore
    @OneToMany(mappedBy = "turnir", cascade = CascadeType.ALL)
    private List<Igrac> igraci;
}
