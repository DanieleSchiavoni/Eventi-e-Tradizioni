package org.elis.progettoesempio.service.location;
import java.util.List;

import org.elis.progettoesempio.dto.LuogoDTO;

public interface LocationService {
    List<LuogoDTO> findAll();
    List<LuogoDTO> findByCategory(Long categoryId);
    LuogoDTO findById(Long id);
    LuogoDTO create(LuogoDTO dto);
    LuogoDTO update(Long id, LuogoDTO dto);
    void delete(Long id);
}
 