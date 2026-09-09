package org.e.filerouge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/villes")
@RequiredArgsConstructor
public class VilleController {

    private final VilleService service;

    @GetMapping
    public List<Ville> all() {
        return service.findAll();
    }

    @PostMapping
    public Ville create(@RequestBody Ville v) {
        return service.create(v);
    }
}
