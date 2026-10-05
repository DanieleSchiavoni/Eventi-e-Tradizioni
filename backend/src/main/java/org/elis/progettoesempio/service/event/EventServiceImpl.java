package org.elis.progettoesempio.service.event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.elis.progettoesempio.dto.EventoDTO;
import org.elis.progettoesempio.exception.ResourceNotFoundException;
import org.elis.progettoesempio.model.Categoria;
import org.elis.progettoesempio.model.Evento;
import org.elis.progettoesempio.model.Luogo;
import org.elis.progettoesempio.repository.CategoriaRepository;
import org.elis.progettoesempio.repository.EventoRepository;
import org.elis.progettoesempio.repository.LuogoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService {
 
    private final EventoRepository eventRepository;
    private final LuogoRepository locationRepository;
    private final CategoriaRepository categoryRepository;
 
    @Override
    @Transactional(readOnly = true)
    public List<EventoDTO> findAll() {
        return eventRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }
 
    @Override
    @Transactional(readOnly = true)
    public List<EventoDTO> search(Long categoryId, LocalDateTime from, LocalDateTime to) {
        return eventRepository.search(categoryId, from, to).stream().map(this::toDTO).collect(Collectors.toList());
    }
 
    @Override
    @Transactional(readOnly = true)
    public List<EventoDTO> findUpcoming() {
        return eventRepository.findUpcomingEvents(LocalDateTime.now()).stream().map(this::toDTO).collect(Collectors.toList());
    }
 
    @Override
    @Transactional(readOnly = true)
    public EventoDTO findById(Long id) {
        return toDTO(getEntity(id));
    }
 
    @Override
    public EventoDTO create(EventoDTO dto) {
        Evento event = Evento.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .luogo(resolveLocation(dto.getLocationId()))
                .categoria(resolveCategory(dto.getCategoryId()))
                .imageUrl(dto.getImageUrl())
                .organizerName(dto.getOrganizerName())
                .build();
        return toDTO(eventRepository.save(event));
    }
 
    @Override
    public EventoDTO update(Long id, EventoDTO dto) {
        Evento event = getEntity(id);
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setStartDate(dto.getStartDate());
        event.setEndDate(dto.getEndDate());
        event.setLuogo(resolveLocation(dto.getLocationId()));
        event.setCategoria(resolveCategory(dto.getCategoryId()));
        event.setImageUrl(dto.getImageUrl());
        event.setOrganizerName(dto.getOrganizerName());
        return toDTO(eventRepository.save(event));
    }
 
    @Override
    public void delete(Long id) {
        eventRepository.delete(getEntity(id));
    }
 
    private Evento getEntity(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento", id));
    }
 
    private Luogo resolveLocation(Long id) {
        if (id == null) return null;
        return locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Punto di interesse", id));
    }
 
    private Categoria resolveCategory(Long id) {
        if (id == null) return null;
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", id));
    }
 
    private EventoDTO toDTO(Evento e) {
        return EventoDTO.builder()
                .id(e.getId())
                .title(e.getTitle())
                .description(e.getDescription())
                .startDate(e.getStartDate())
                .endDate(e.getEndDate())
                .locationId(e.getLuogo() != null ? e.getLuogo().getId() : null)
                .locationName(e.getLuogo() != null ? e.getLuogo().getName() : null)
                .categoryId(e.getCategoria() != null ? e.getCategoria().getId() : null)
                .categoryName(e.getCategoria() != null ? e.getCategoria().getNome() : null)
                .categoryIcon(e.getCategoria() != null ? e.getCategoria().getIcona() : null)
                .imageUrl(e.getImageUrl())
                .organizerName(e.getOrganizerName())
                .build();
    }
}
 