package org.e.filerouge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategorieController {

    private final CategorieService service;

    @GetMapping
    public List<Categorie> all() {
        return service.findAll();
    }

    @PostMapping
    public Categorie create(@RequestBody Categorie c) {
        return service.create(c);
    }
}
