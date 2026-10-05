package org.elis.progettoesempio.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {
 
    @Builder.Default
    private String type = "Bearer";
    private String token;
    private Long id;
    private String username;
    private String email;
    private String role;
}
 