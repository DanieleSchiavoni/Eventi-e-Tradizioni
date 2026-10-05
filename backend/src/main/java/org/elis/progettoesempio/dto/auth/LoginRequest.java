package org.elis.progettoesempio.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    // Rinominato da "username": Utente non ne ha uno, il login avviene per email
    @NotBlank(message = "L'email e' obbligatoria")
    private String email;

    @NotBlank(message = "La password e' obbligatoria")
    private String password;
}