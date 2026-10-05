package org.elis.progettoesempio.service;


import java.util.List;

import org.elis.progettoesempio.dto.CategoriaDTO;
 
public interface CategoryService {
    List<CategoriaDTO> findAll();
    CategoriaDTO findById(Long id);
    CategoriaDTO create(CategoriaDTO dto);
    CategoriaDTO update(Long id, CategoriaDTO dto);
    void delete(Long id);
}
 
