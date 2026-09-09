package org.e.filerouge.service;

import org.e.filerouge.dto.auth.owner.OwnerResponse;

public interface OwnerService {

    OwnerResponse getById(Long id);
}
