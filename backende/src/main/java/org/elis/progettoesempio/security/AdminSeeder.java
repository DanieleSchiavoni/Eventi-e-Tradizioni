package org.elis.progettoesempio.security;

import org.elis.progettoesempio.model.Ruolo;
import org.elis.progettoesempio.model.Utente;
import org.elis.progettoesempio.repository.UtenteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Crea un utente amministratore all'avvio dell'app, se non esiste gia'.
 * Utile in sviluppo: l'endpoint /api/auth/register assegna sempre RUOLO_USER,
 * quindi senza questo seeder non ci sarebbe alcun modo di ottenere un account ADMIN.
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class AdminSeeder {

    private static final String ADMIN_EMAIL = "admin@avetrana.it";
    private static final String ADMIN_PASSWORD = "Admin123!";

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner seedAdmin() {
        return args -> {
            if (utenteRepository.existsByEmail(ADMIN_EMAIL)) {
                log.info("Utente admin gia' presente ({})", ADMIN_EMAIL);
                return;
            }

            Utente admin = Utente.builder()
                    .nome("Admin")
                    .cognome("Avetrana")
                    .email(ADMIN_EMAIL)
                    .password(passwordEncoder.encode(ADMIN_PASSWORD))
                    .ruolo(Ruolo.RUOLO_ADMIN)
                    .build();

            utenteRepository.save(admin);
            log.info("Utente admin creato: {} / {}", ADMIN_EMAIL, ADMIN_PASSWORD);
        };
    }
}