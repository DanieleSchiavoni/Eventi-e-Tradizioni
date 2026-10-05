package org.elis.progettoesempio.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.elis.progettoesempio.dto.EventoDTO;
import org.elis.progettoesempio.service.event.EventService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventoController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventoDTO>> findAll(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {

        if (categoryId != null || from != null || to != null) {
            return ResponseEntity.ok(eventService.search(categoryId, from, to));
        }
        return ResponseEntity.ok(eventService.findAll());
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<EventoDTO>> findUpcoming() {
        return ResponseEntity.ok(eventService.findUpcoming());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EventoDTO> create(@Valid @RequestBody EventoDTO dto) {
        return ResponseEntity.status(201).body(eventService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoDTO> update(@PathVariable Long id, @Valid @RequestBody EventoDTO dto) {
        return ResponseEntity.ok(eventService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }
}