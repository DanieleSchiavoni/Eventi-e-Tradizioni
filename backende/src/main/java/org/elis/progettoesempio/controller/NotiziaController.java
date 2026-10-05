package org.elis.progettoesempio.controller;

import java.util.List;

import org.elis.progettoesempio.dto.NotiziaDTO;
import org.elis.progettoesempio.service.notizia.NoticeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NotiziaController {

    private final NoticeService noticeService;

    @GetMapping
    public ResponseEntity<List<NotiziaDTO>> findAll() {
        return ResponseEntity.ok(noticeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotiziaDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(noticeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<NotiziaDTO> create(@Valid @RequestBody NotiziaDTO dto) {
        return ResponseEntity.status(201).body(noticeService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotiziaDTO> update(@PathVariable Long id, @Valid @RequestBody NotiziaDTO dto) {
        return ResponseEntity.ok(noticeService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        noticeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}