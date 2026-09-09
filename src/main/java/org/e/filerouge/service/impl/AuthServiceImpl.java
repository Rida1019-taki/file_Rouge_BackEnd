package org.e.filerouge.service.impl;

import lombok.RequiredArgsConstructor;
import org.e.filerouge.dto.auth.AuthResponse;
import org.e.filerouge.dto.auth.LoginRequest;
import org.e.filerouge.dto.auth.RegisterRequest;
import org.e.filerouge.entity.Client;
import org.e.filerouge.entity.Owner;
import org.e.filerouge.entity.Utilisateur;
import org.e.filerouge.enums.Role;
import org.e.filerouge.repository.ClientRepository;
import org.e.filerouge.repository.OwnerRepository;
import org.e.filerouge.repository.UtilisateurRepository;
import org.e.filerouge.security.JwtUtil;
import org.e.filerouge.service.AuthService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UtilisateurRepository repo;
    private final ClientRepository clients;
    private final OwnerRepository owners;
    private final PasswordEncoder encoder;
    private final AuthenticationManager auth;
    private final JwtUtil jwt;

    @Override
    @CacheEvict(value = "stats_users", allEntries = true)
    public AuthResponse register(RegisterRequest r) {
        if (repo.findByEmail(r.email()).isPresent()) {
            throw new IllegalArgumentException("Email déjà utilisé");
        }

        Utilisateur u = new Utilisateur(null, r.nom(), r.prenom(), r.email(), r.telephone(), encoder.encode(r.password()), r.role(), true);
        u = repo.saveAndFlush(u);

        if (u.getRole() == Role.CLIENT) {
            Client client = new Client();
            client.setUtilisateur(u);
            clients.saveAndFlush(client);
        } else if (u.getRole() == Role.OWNER) {
            Owner owner = new Owner();
            owner.setUtilisateur(u);
            owners.saveAndFlush(owner);
        }

        return new AuthResponse(jwt.generateToken(u), u.getId(), u.getNom(), u.getPrenom(), u.getEmail(), u.getRole().name());
    }

    @Override
    public AuthResponse login(LoginRequest r) {
        var a = auth.authenticate(new UsernamePasswordAuthenticationToken(r.email(), r.password()));
        Utilisateur u = (Utilisateur) a.getPrincipal();
        return new AuthResponse(jwt.generateToken(u), u.getId(), u.getNom(), u.getPrenom(), u.getEmail(), u.getRole().name());
    }
}
