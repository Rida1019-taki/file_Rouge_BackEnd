package org.e.filerouge.service.impl;

import lombok.RequiredArgsConstructor;
import org.e.filerouge.entity.Categorie;
import org.e.filerouge.repository.CategorieRepository;
import org.e.filerouge.service.CategorieService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CategorieServiceImpl implements CategorieService {

    private final CategorieRepository repo;

    @Override
    @Cacheable(value = "categories")
    public List<Categorie> findAll() {
        return repo.findAll();
    }

    @Override
    @CacheEvict(value = "categories", allEntries = true)
    public Categorie create(Categorie c) {
        return repo.save(c);
    }
}
