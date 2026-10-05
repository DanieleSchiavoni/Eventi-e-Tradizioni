package org.elis.progettoesempio.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import org.elis.progettoesempio.model.Utente;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.util.Date;
 
@Component
@Slf4j
public class JwtUtils {
 
    @Value("${app.jwt.secret}")
    private String jwtSecret;
 
    @Value("${app.jwt.expiration-ms}")
    private long jwtExpirationMs;
 
    private SecretKey key() {
        return Keys.hmacShaKeyFor(java.util.Base64.getDecoder().decode(jwtSecret));
    }
 
    public String generateJwtToken(Authentication authentication) {
        Utente userPrincipal = (Utente) authentication.getPrincipal();
        // getUsername() su Utente ritorna l'email: e' l'identificativo usato per il login
        return generateTokenFromUsername(userPrincipal.getUsername());
    }
 
    public String generateTokenFromUsername(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
 
        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key())
                .compact();
    }
 
    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
 
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().verifyWith(key()).build().parseSignedClaims(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Token JWT non valido: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("Token JWT scaduto: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Token JWT non supportato: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("La stringa claims JWT e' vuota: {}", e.getMessage());
        }
        return false;
    }
}