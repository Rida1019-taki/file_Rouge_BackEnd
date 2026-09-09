package org.e.filerouge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owner")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService service;

    @GetMapping("/{id}")
    public OwnerResponse get(@PathVariable Long id) {
        return service.getById(id);
    }
}
