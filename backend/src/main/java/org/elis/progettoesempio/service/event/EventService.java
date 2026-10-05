package org.elis.progettoesempio.service.event;

import java.time.LocalDateTime;
import java.util.List;

import org.elis.progettoesempio.dto.EventoDTO;
 
public interface EventService {
    List<EventoDTO> findAll();
    List<EventoDTO> search(Long categoryId, LocalDateTime from, LocalDateTime to);
    List<EventoDTO> findUpcoming();
    EventoDTO findById(Long id);
    EventoDTO create(EventoDTO dto);
    EventoDTO update(Long id, EventoDTO dto);
    void delete(Long id);
}
 