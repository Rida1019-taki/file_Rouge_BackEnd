package org.e.filerouge.service.impl;

import lombok.RequiredArgsConstructor;
import org.e.filerouge.dto.auth.client.ClientResponse;
import org.e.filerouge.exception.ResourceNotFoundException;
import org.e.filerouge.mapper.UtilisateurMapper;
import org.e.filerouge.repository.UtilisateurRepository;
import org.e.filerouge.service.ClientService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final UtilisateurRepository repo;
    private final UtilisateurMapper mapper;

    @Override
    @Cacheable(value = "clients", key = "#id")
    @Transactional(readOnly = true)
    public ClientResponse getById(Long id) {
        var u = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client introuvable"));
        return mapper.toClient(u);
    }
}
