package org.elis.progettoesempio.repository;


import org.elis.progettoesempio.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Long> {

	Optional<Utente> findByEmail(String email);
	Boolean existsByEmail(String email);
}
