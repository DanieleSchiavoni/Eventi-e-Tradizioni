package org.elis.progettoesempio.service;

import org.elis.progettoesempio.dto.auth.JwtResponse;
import org.elis.progettoesempio.dto.auth.LoginRequest;
import org.elis.progettoesempio.dto.auth.RegisterRequest;

public interface AuthService {
    JwtResponse login(LoginRequest request);
    JwtResponse register(RegisterRequest request);
}