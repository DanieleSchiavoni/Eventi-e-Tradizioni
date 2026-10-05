package org.elis.progettoesempio.repository;

import java.util.List;

import org.elis.progettoesempio.model.Luogo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LuogoRepository extends JpaRepository<Luogo, Long>{
	
	List<Luogo> findByCategoria_Id(Long categoryId);

}
