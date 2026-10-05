package org.elis.progettoesempio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
import java.time.LocalDateTime;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventoDTO {
    private Long id;
 
    @NotBlank(message = "Il titolo e' obbligatorio")
    private String title;
 
    private String description;
 
    @NotNull(message = "La data di inizio e' obbligatoria")
    private LocalDateTime startDate;
 
    private LocalDateTime endDate;
 
    private Long locationId;
    private String locationName;
 
    private Long categoryId;
    private String categoryName;
    private String categoryIcon;
 
    private String imageUrl;
    private String organizerName;
}