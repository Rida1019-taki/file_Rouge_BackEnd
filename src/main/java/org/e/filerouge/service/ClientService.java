package org.e.filerouge.service;

import org.e.filerouge.dto.auth.client.ClientResponse;

public interface ClientService {

    ClientResponse getById(Long id);
}
