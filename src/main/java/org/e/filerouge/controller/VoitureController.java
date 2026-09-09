package org.e.filerouge.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/voitures")
@RequiredArgsConstructor
public class VoitureController {

    private final VoitureService service;

    @GetMapping
    public Object all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public VoitureResponse one(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<VoitureResponse> create(@Valid @RequestBody VoitureRequest r, Authentication a) {
        return ResponseEntity.status(201).body(service.create(r, Long.parseLong(((Utilisateur) a.getPrincipal()).getId().toString())));
    }

    @PutMapping("/{id}")
    public VoitureResponse update(@PathVariable Long id, @Valid @RequestBody VoitureRequest r, Authentication a) {
        return service.update(id, r, ((Utilisateur) a.getPrincipal()).getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication a) {
        service.delete(id, ((Utilisateur) a.getPrincipal()).getId());
        return ResponseEntity.noContent().build();
    }
}
