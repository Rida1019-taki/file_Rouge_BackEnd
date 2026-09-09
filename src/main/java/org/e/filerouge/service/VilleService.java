package org.e.filerouge.service;

import org.e.filerouge.entity.Ville;

import java.util.List;

public interface VilleService {

    List<Ville> findAll();

    Ville create(Ville v);
}
