package org.elis.progettoesempio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
import java.time.LocalDateTime;

import org.elis.progettoesempio.model.Priorita;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotiziaDTO {
    private Long id;
 
    @NotBlank(message = "Il titolo e' obbligatorio")
    private String title;
 
    @NotBlank(message = "Il contenuto e' obbligatorio")
    private String content;
 
    @NotNull(message = "La priorita' e' obbligatoria")
    private Priorita priorita;
 
    private LocalDateTime publishedAt;

    // Collegamento OPZIONALE a un evento (nessuna @NotNull: puo' restare vuoto)
    private Long eventoId;
    private String eventoTitle;
}