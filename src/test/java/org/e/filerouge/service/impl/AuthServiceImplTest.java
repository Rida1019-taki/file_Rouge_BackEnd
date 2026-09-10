package org.e.filerouge.service.impl;

import org.e.filerouge.dto.auth.AuthResponse;
import org.e.filerouge.dto.auth.LoginRequest;
import org.e.filerouge.entity.Utilisateur;
import org.e.filerouge.enums.Role;
import org.e.filerouge.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void testLogin_Success() {
        // Arrange
        LoginRequest request = new LoginRequest("test@email.com", "password123");
        Utilisateur utilisateur = new Utilisateur(1L, "Nom", "Prenom", "test@email.com", "0600000000", "password123", Role.CLIENT, true);
        
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(utilisateur);
        when(jwtUtil.generateToken(utilisateur)).thenReturn("mocked-jwt-token");

        // Act
        AuthResponse response = authService.login(request);

        // Assert
        assertNotNull(response);
        assertEquals("mocked-jwt-token", response.token());
        assertEquals("test@email.com", response.email());
        assertEquals("Nom", response.nom());
    }
}
