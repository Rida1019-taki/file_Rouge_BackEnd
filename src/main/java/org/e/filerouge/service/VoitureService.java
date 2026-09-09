package org.e.filerouge.service;

import org.e.filerouge.dto.auth.voiture.VoitureRequest;
import org.e.filerouge.dto.auth.voiture.VoitureResponse;

import java.util.List;

public interface VoitureService {

    List<VoitureResponse> findAll();

    VoitureResponse getById(Long id);

    VoitureResponse create(VoitureRequest r, Long ownerId);

    VoitureResponse update(Long id, VoitureRequest r, Long ownerId);

    void delete(Long id, Long ownerId);
}
