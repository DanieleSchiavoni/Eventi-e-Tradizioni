package org.elis.progettoesempio.controller;

import java.util.List;

import org.elis.progettoesempio.dto.LuogoDTO;
import org.elis.progettoesempio.service.location.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LuogoController {

    private final LocationService locationService;

    @GetMapping
    public ResponseEntity<List<LuogoDTO>> findAll(@RequestParam(required = false) Long categoryId) {
        if (categoryId != null) {
            return ResponseEntity.ok(locationService.findByCategory(categoryId));
        }
        return ResponseEntity.ok(locationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LuogoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.findById(id));
    }

    @PostMapping
    public ResponseEntity<LuogoDTO> create(@Valid @RequestBody LuogoDTO dto) {
        return ResponseEntity.status(201).body(locationService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LuogoDTO> update(@PathVariable Long id, @Valid @RequestBody LuogoDTO dto) {
        return ResponseEntity.ok(locationService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        locationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}