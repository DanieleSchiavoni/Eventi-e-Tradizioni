package org.elis.progettoesempio.service;

import java.util.List;
import java.util.stream.Collectors;

import org.elis.progettoesempio.dto.CategoriaDTO;
import org.elis.progettoesempio.exception.ResourceNotFoundException;
import org.elis.progettoesempio.model.Categoria;
import org.elis.progettoesempio.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
 
    private final CategoriaRepository categoryRepository;
 
    @Override
    @Transactional(readOnly = true)
    public List<CategoriaDTO> findAll() {
        return categoryRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }
 
    @Override
    @Transactional(readOnly = true)
    public CategoriaDTO findById(Long id) {
        return toDTO(getEntity(id));
    }
 
    @Override
    public CategoriaDTO create(CategoriaDTO dto) {
        Categoria category = Categoria.builder()
                .nome(dto.getNome())
                .icona(dto.getIcona())
                .build();
        return toDTO(categoryRepository.save(category));
    }
 
    @Override
    public CategoriaDTO update(Long id, CategoriaDTO dto) {
        Categoria category = getEntity(id);
        category.setNome(dto.getNome());
        category.setIcona(dto.getIcona());
        return toDTO(categoryRepository.save(category));
    }
 
    @Override
    public void delete(Long id) {
        Categoria category = getEntity(id);
        categoryRepository.delete(category);
    }
 
    private Categoria getEntity(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", id));
    }
 
    private CategoriaDTO toDTO(Categoria c) {
        return CategoriaDTO.builder()
                .id(c.getId())
                .nome(c.getNome())
                .icona(c.getIcona())
                .build();
    }
}
 