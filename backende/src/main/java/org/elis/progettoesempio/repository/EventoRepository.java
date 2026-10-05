package org.elis.progettoesempio.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.elis.progettoesempio.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    // Derived method matching e.categoria.id
    List<Evento> findByCategoria_Id(Long categoryId);

    List<Evento> findByStartDateBetween(LocalDateTime from, LocalDateTime to);

    @Query("SELECT e FROM Evento e WHERE e.startDate >= :now ORDER BY e.startDate ASC")
    List<Evento> findUpcomingEvents(@Param("now") LocalDateTime now);

    @Query("SELECT e FROM Evento e WHERE " +
           "(:categoryId IS NULL OR e.categoria.id = :categoryId) AND " +
           "(:from IS NULL OR e.startDate >= :from) AND " +
           "(:to IS NULL OR e.startDate <= :to) " +
           "ORDER BY e.startDate ASC")
    List<Evento> search(@Param("categoryId") Long categoryId,
                        @Param("from") LocalDateTime from,
                        @Param("to") LocalDateTime to);
}