package org.e.filerouge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
