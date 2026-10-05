package org.elis.progettoesempio.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
 
	@NotBlank(message = "Il nome e' obbligatorio")
    private String nome;

    @NotBlank(message = "Il cognome e' obbligatorio")
    private String cognome;
 
    @NotBlank(message = "L'email e' obbligatoria")
    @Email(message = "Formato email non valido")
    private String email;
 
    @NotBlank(message = "La password e' obbligatoria")
    @Size(min = 6, message = "La password deve contenere almeno 6 caratteri")
    private String password;
}
 