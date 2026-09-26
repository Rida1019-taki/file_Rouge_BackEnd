package org.e.filerouge.controller;

import org.e.filerouge.entity.Utilisateur;
import org.e.filerouge.enums.Role;
import org.e.filerouge.repository.UtilisateurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    private WebApplicationContext ctx;
    @Autowired
    private UtilisateurRepository repo;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx).apply(springSecurity()).build();
    }

    @Test
    void loginWithUnknownUserReturnsUnauthorized() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"inconnu@test.com\",\"password\":\"wrong\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void loginWithWrongPasswordReturnsUnauthorized() throws Exception {
        repo.save(new Utilisateur(null, "Test", "Client", "login-invalide@test.com", "0600000100", "correctPassword", Role.CLIENT));
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"login-invalide@test.com\",\"password\":\"wrongPassword\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void loginWithValidCredentialsReturnsOk() throws Exception {
        var hash = new BCryptPasswordEncoder().encode("password123");
        repo.save(new Utilisateur(null, "Test", "Client", "login-valide@test.com", "0600000101", hash, Role.CLIENT));
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"login-valide@test.com\",\"password\":\"password123\"}"))
                .andExpect(status().isOk());
    }
}