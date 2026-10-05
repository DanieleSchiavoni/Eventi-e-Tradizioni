package org.elis.progettoesempio.service.notizia;

import java.util.List;

import org.elis.progettoesempio.dto.NotiziaDTO;

public interface NoticeService {
    List<NotiziaDTO> findAll();
    NotiziaDTO findById(Long id);
    NotiziaDTO create(NotiziaDTO dto);
    NotiziaDTO update(Long id, NotiziaDTO dto);
    void delete(Long id);
}
 