package com.projekt1.tournament_manager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "korisnik")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Ime ne smije biti prazno!")
    @Column(nullable = false)
    private String ime;

    @NotBlank(message = "Prezime ne smije biti prazno!")
    @Column(nullable = false)
    private String prezime;

    @Email(message = "Email nije ispravan!")
    @NotBlank(message = "Email ne smije biti prazan!")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Lozinka ne smije biti prazna!")
    @Column(nullable = false)
    private String lozinka;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Rola rola = Rola.USER;
}
