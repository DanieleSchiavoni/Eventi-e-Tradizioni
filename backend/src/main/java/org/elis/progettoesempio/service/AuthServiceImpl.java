package org.elis.progettoesempio.service;

import org.elis.progettoesempio.dto.auth.JwtResponse;
import org.elis.progettoesempio.dto.auth.LoginRequest;
import org.elis.progettoesempio.dto.auth.RegisterRequest;
import org.elis.progettoesempio.exception.BadRequestException;
import org.elis.progettoesempio.model.Ruolo;
import org.elis.progettoesempio.model.Utente;
import org.elis.progettoesempio.repository.UtenteRepository;
import org.elis.progettoesempio.security.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UtenteRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public JwtResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);
        Utente principal = (Utente) authentication.getPrincipal();

        return JwtResponse.builder()
                .token(jwt)
                .id(principal.getId())
                // getUsername() su Utente ritorna l'email: e' l'identificativo di login
                .username(principal.getUsername())
                .email(principal.getEmail())
                .role(principal.getRuolo().name())
                .build();
    }

    @Override
    public JwtResponse register(RegisterRequest request) {
        // NB: Utente non ha un campo "username" separato, il login avviene per email.
        // Adatta i nomi dei getter di RegisterRequest ai campi realmente presenti nel tuo DTO.
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email gia' in uso");
        }

        Utente user = Utente.builder()
                .nome(request.getNome())
                .cognome(request.getCognome())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .ruolo(Ruolo.RUOLO_USER)
                .build();

        userRepository.save(user);

        // Login automatico dopo la registrazione (LoginRequest usa l'email come "username")
        return login(new LoginRequest(request.getEmail(), request.getPassword()));
    }
}