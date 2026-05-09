package com.projekt1.tournament_manager.service;

import com.projekt1.tournament_manager.dto.AuthResponse;
import com.projekt1.tournament_manager.dto.LoginRequest;
import com.projekt1.tournament_manager.dto.RegisterRequest;
import com.projekt1.tournament_manager.exception.ResourceNotFoundException;
import com.projekt1.tournament_manager.model.Korisnik;
import com.projekt1.tournament_manager.repository.KorisnikRepository;
import com.projekt1.tournament_manager.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final KorisnikRepository korisnikRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request) {
        if (korisnikRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email je već zauzet");
        }

        Korisnik korisnik = Korisnik.builder()
                .ime(request.getIme())
                .prezime(request.getPrezime())
                .email(request.getEmail())
                .lozinka(passwordEncoder.encode(request.getLozinka()))
                .build();

        korisnikRepository.save(korisnik);

        String token = jwtService.generirajToken(korisnik.getEmail(), korisnik.getRola().name());

        return new AuthResponse(token, korisnik.getEmail(), korisnik.getRola().name());
    }

    public AuthResponse login(LoginRequest request) {
        Korisnik korisnik = korisnikRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Korisnik s emailom " + request.getEmail() + " ne postoji"));

        if (!passwordEncoder.matches(request.getLozinka(), korisnik.getLozinka())) {
            throw new IllegalArgumentException("Pogrešna lozinka");
        }

        String token = jwtService.generirajToken(korisnik.getEmail(), korisnik.getRola().name());

        return new AuthResponse(token, korisnik.getEmail(), korisnik.getRola().name());
    }
}