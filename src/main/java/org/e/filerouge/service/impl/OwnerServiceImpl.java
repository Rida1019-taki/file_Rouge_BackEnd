package org.e.filerouge.service.impl;

import lombok.RequiredArgsConstructor;
import org.e.filerouge.dto.auth.owner.OwnerResponse;
import org.e.filerouge.entity.Owner;
import org.e.filerouge.exception.ResourceNotFoundException;
import org.e.filerouge.repository.OwnerRepository;
import org.e.filerouge.service.OwnerService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository repo;

    @Override
    @Cacheable(value = "owners", key = "#id")
    @Transactional(readOnly = true)
    public OwnerResponse getById(Long id) {
        Owner o = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Owner introuvable"));
        var u = o.getUtilisateur();
        return new OwnerResponse(o.getId(), u.getNom(), u.getPrenom(), u.getEmail(), u.getTelephone(), o.getTypeOwner() == null ? null : o.getTypeOwner().name(), o.getNomEntreprise());
    }
}
