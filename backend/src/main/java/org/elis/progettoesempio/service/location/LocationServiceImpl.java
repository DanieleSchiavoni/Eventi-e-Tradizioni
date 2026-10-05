package org.elis.progettoesempio.service.location;

import java.util.List;
import java.util.stream.Collectors;

import org.elis.progettoesempio.dto.LuogoDTO;
import org.elis.progettoesempio.exception.ResourceNotFoundException;
import org.elis.progettoesempio.model.Categoria;
import org.elis.progettoesempio.model.Luogo;
import org.elis.progettoesempio.repository.CategoriaRepository;
import org.elis.progettoesempio.repository.LuogoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class LocationServiceImpl implements LocationService {
 
    private final LuogoRepository locationRepository;
    private final CategoriaRepository categoryRepository;
 
    @Override
    @Transactional(readOnly = true)
    public List<LuogoDTO> findAll() {
        return locationRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }
 
    @Override
    @Transactional(readOnly = true)
    public List<LuogoDTO> findByCategory(Long categoryId) {
        return locationRepository.findByCategoria_Id(categoryId).stream().map(this::toDTO).collect(Collectors.toList());
    }
 
    @Override
    @Transactional(readOnly = true)
    public LuogoDTO findById(Long id) {
        return toDTO(getEntity(id));
    }
 
    @Override
    public LuogoDTO create(LuogoDTO dto) {
        Luogo location = Luogo.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .address(dto.getAddress())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .imageUrl(dto.getImageUrl())
                .categoria(resolveCategory(dto.getCategoriaId()))
                .build();
        return toDTO(locationRepository.save(location));
    }
 
    @Override
    public LuogoDTO update(Long id, LuogoDTO dto) {
        Luogo location = getEntity(id);
        location.setName(dto.getName());
        location.setDescription(dto.getDescription());
        location.setAddress(dto.getAddress());
        location.setLatitude(dto.getLatitude());
        location.setLongitude(dto.getLongitude());
        location.setImageUrl(dto.getImageUrl());
        location.setCategoria(resolveCategory(dto.getCategoriaId()));
        return toDTO(locationRepository.save(location));
    }
 
    @Override
    public void delete(Long id) {
        locationRepository.delete(getEntity(id));
    }
 
    private Luogo getEntity(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Punto di interesse", id));
    }
 
    private Categoria resolveCategory(Long categoryId) {
        if (categoryId == null) return null;
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", categoryId));
    }
 
    private LuogoDTO toDTO(Luogo l) {
        return LuogoDTO.builder()
                .id(l.getId())
                .name(l.getName())
                .description(l.getDescription())
                .address(l.getAddress())
                .latitude(l.getLatitude())
                .longitude(l.getLongitude())
                .imageUrl(l.getImageUrl())
                .categoriaId(l.getCategoria() != null ? l.getCategoria().getId() : null)
                .categoriaName(l.getCategoria() != null ? l.getCategoria().getNome() : null)
                .categoriaIcon(l.getCategoria() != null ? l.getCategoria().getIcona() : null)
                .build();
    }
}
 