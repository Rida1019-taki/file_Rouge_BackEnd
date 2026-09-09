package org.e.filerouge.service;

import org.e.filerouge.entity.Categorie;

import java.util.List;

public interface CategorieService {

    List<Categorie> findAll();

    Categorie create(Categorie c);
}
