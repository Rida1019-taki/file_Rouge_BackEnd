package org.e.filerouge.controller;

import org.e.filerouge.entity.Utilisateur;
import org.e.filerouge.enums.Role;
import org.e.filerouge.repository.UtilisateurRepository;
import org.e.filerouge.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private WebApplicationContext ctx;
    @Autowired
    private UtilisateurRepository repo;
    @Autowired
    private JwtUtil jwt;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx).apply(springSecurity()).build();
    }

    @Test
    void unauthenticatedRequestIsForbidden() throws Exception {
        mockMvc.perform(get("/api/users")).andExpect(status().isForbidden());
    }

    @Test
    void nonAdminRoleIsForbidden() throws Exception {
        repo.save(new Utilisateur(null, "Test", "Client", "client-sec@test.com", "0600000000", "x", Role.CLIENT, true));
        var client = repo.findByEmail("client-sec@test.com").orElseThrow();
        mockMvc.perform(get("/api/users").header("Authorization", "Bearer " + jwt.generateToken(client)))
                .andExpect(status().isForbidden());
    }

    @Test
    void adminCanListUsers() throws Exception {
        repo.save(new Utilisateur(null, "Test", "Admin", "admin-sec@test.com", "0600000001", "x", Role.ADMIN, true));
        var admin = repo.findByEmail("admin-sec@test.com").orElseThrow();
        mockMvc.perform(get("/api/users").header("Authorization", "Bearer " + jwt.generateToken(admin)))
                .andExpect(status().isOk());
    }
}