
package org.elis.progettoesempio.security;
 
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;
 
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
 
@Component
@RequiredArgsConstructor
@Slf4j
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
 
    // Spring Boot 4 autoconfigura un bean JsonMapper (Jackson 3, package tools.jackson)
    // al posto del vecchio ObjectMapper di com.fasterxml.jackson: lo riusiamo invece di crearne uno nuovo.
    private final JsonMapper jsonMapper;
 
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        log.error("Errore di autenticazione: {}", authException.getMessage());
 
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
 
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", "Accesso negato: e' necessario autenticarsi");
        body.put("path", request.getServletPath());
 
        jsonMapper.writeValue(response.getOutputStream(), body);
    }
}
 