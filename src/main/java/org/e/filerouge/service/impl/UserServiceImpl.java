package org.e.filerouge.service.impl;

import lombok.RequiredArgsConstructor;
import org.e.filerouge.dto.admin.*;
import org.e.filerouge.entity.Utilisateur;
import org.e.filerouge.exception.ResourceNotFoundException;
import org.e.filerouge.repository.UtilisateurRepository;
import org.e.filerouge.service.UserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UtilisateurRepository repo;

    @Override
    public List<UserResponse> findAll() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public UserResponse findById(Long id) {
        return toResponse(find(id));
    }

    @Override
    @CacheEvict(value = "stats_users", allEntries = true)
    public UserResponse update(Long id, UserUpdateRequest request) {
        Utilisateur u = find(id);
        if (request.nom() != null) u.setNom(request.nom());
        if (request.prenom() != null) u.setPrenom(request.prenom());
        if (request.telephone() != null) u.setTelephone(request.telephone());
        if (request.actif() != null) u.setActif(request.actif());
        return toResponse(repo.save(u));
    }

    @Override
    @CacheEvict(value = "stats_users", allEntries = true)
    public void delete(Long id) {
        find(id);
        repo.deleteById(id);
    }

    private Utilisateur find(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
    }

    private UserResponse toResponse(Utilisateur u) {
        return new UserResponse(u.getId(), u.getNom(), u.getPrenom(), u.getEmail(), u.getTelephone(), u.getRole().name(), u.isActif());
    }
}