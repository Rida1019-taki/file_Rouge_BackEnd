package org.e.filerouge.controller;

import lombok.RequiredArgsConstructor;
import org.e.filerouge.service.ClientService;
import org.springframework.web.bind.annotation.*;
import org.e.filerouge.dto.auth.client.ClientResponse;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;

    @GetMapping("/{id}")
    public ClientResponse get(@PathVariable Long id) {
        return service.getById(id);
    }
}
