package org.elis.progettoesempio.service.notizia;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.elis.progettoesempio.dto.NotiziaDTO;
import org.elis.progettoesempio.exception.ResourceNotFoundException;
import org.elis.progettoesempio.model.Evento;
import org.elis.progettoesempio.model.Notizia;
import org.elis.progettoesempio.repository.EventoRepository;
import org.elis.progettoesempio.repository.NotiziaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeServiceImpl implements NoticeService {
 
    private final NotiziaRepository noticeRepository;
    private final EventoRepository eventoRepository;
 
    @Override
    @Transactional(readOnly = true)
    public List<NotiziaDTO> findAll() {
        return noticeRepository.findAllByOrderByPublishedAtDesc().stream().map(this::toDTO).collect(Collectors.toList());
    }
 
    @Override
    @Transactional(readOnly = true)
    public NotiziaDTO findById(Long id) {
        return toDTO(getEntity(id));
    }
 
    @Override
    public NotiziaDTO create(NotiziaDTO dto) {
        Notizia notice = Notizia.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .priority(dto.getPriorita())
                .publishedAt(dto.getPublishedAt() != null ? dto.getPublishedAt() : LocalDateTime.now())
                .evento(resolveEvento(dto.getEventoId()))
                .build();
        return toDTO(noticeRepository.save(notice));
    }
 
    @Override
    public NotiziaDTO update(Long id, NotiziaDTO dto) {
        Notizia notice = getEntity(id);
        notice.setTitle(dto.getTitle());
        notice.setContent(dto.getContent());
        notice.setPriority(dto.getPriorita());
        if (dto.getPublishedAt() != null) {
            notice.setPublishedAt(dto.getPublishedAt());
        }
        notice.setEvento(resolveEvento(dto.getEventoId()));
        return toDTO(noticeRepository.save(notice));
    }
 
    @Override
    public void delete(Long id) {
        noticeRepository.delete(getEntity(id));
    }
 
    private Notizia getEntity(Long id) {
        return noticeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avviso", id));
    }

    private Evento resolveEvento(Long eventoId) {
        if (eventoId == null) {
            return null;
        }
        return eventoRepository.findById(eventoId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento", eventoId));
    }
 
    private NotiziaDTO toDTO(Notizia n) {
        return NotiziaDTO.builder()
                .id(n.getId())
                .title(n.getTitle())
                .content(n.getContent())
                .priorita(n.getPriority())
                .publishedAt(n.getPublishedAt())
                .eventoId(n.getEvento() != null ? n.getEvento().getId() : null)
                .eventoTitle(n.getEvento() != null ? n.getEvento().getTitle() : null)
                .build();
    }
}