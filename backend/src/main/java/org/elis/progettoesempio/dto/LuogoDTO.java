package org.elis.progettoesempio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LuogoDTO {
    private Long id;
 
    @NotBlank(message = "Il nome e' obbligatorio")
    private String name;
 
    private String description;
    private String address;
 
    @NotNull(message = "La latitudine e' obbligatoria")
    private Double latitude;
 
    @NotNull(message = "La longitudine e' obbligatoria")
    private Double longitude;
 
    private String imageUrl;
 
    private Long categoriaId;
    private String categoriaName;
    private String categoriaIcon;
}