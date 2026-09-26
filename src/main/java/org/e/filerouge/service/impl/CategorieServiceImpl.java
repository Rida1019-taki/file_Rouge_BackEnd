package org.e.filerouge.service.impl;

import lombok.RequiredArgsConstructor;
import org.e.filerouge.entity.Categorie;
import org.e.filerouge.repository.CategorieRepository;
import org.e.filerouge.service.CategorieService;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CategorieServiceImpl implements CategorieService {

    private final CategorieRepository repo;

    @Override
    public List<Categorie> findAll() {
        return repo.findAll();
    }

    @Override
    public Categorie create(Categorie c) {
        return repo.save(c);
    }
}
