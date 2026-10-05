package org.elis.progettoesempio.repository;

import java.util.List;

import org.elis.progettoesempio.model.Notizia;
import org.elis.progettoesempio.model.Priorita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotiziaRepository extends JpaRepository<Notizia, Long> {

	List<Notizia> findByPriorityOrderByPublishedAtDesc(Priorita priorita);
	 
    List<Notizia> findAllByOrderByPublishedAtDesc();
}
