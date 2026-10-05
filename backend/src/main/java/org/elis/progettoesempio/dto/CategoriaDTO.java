package org.elis.progettoesempio.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaDTO {
    private Long id;
 
    @NotBlank(message = "Il nome della categoria e' obbligatorio")
    private String nome;
 
    private String icona;
}
