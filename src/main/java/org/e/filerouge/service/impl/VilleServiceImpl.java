package org.e.filerouge.service.impl;

import lombok.RequiredArgsConstructor;
import org.e.filerouge.entity.Ville;
import org.e.filerouge.repository.VilleRepository;
import org.e.filerouge.service.VilleService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class VilleServiceImpl implements VilleService {

    private final VilleRepository repo;

    @Override
    public List<Ville> findAll() {
        return repo.findAll();
    }

    @Override
    public Ville create(Ville v) {
        return repo.save(v);
    }
}
