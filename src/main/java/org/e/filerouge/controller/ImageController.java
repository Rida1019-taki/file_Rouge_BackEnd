package org.e.filerouge.controller;

import lombok.RequiredArgsConstructor;
import org.e.filerouge.entity.Image;
import org.e.filerouge.service.ImageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService service;

    @PostMapping("/voiture/{voitureId}")
    public ResponseEntity<Image> add(@PathVariable Long voitureId, @RequestParam String url, @RequestParam(defaultValue = "false") boolean principale) {
        return ResponseEntity.status(201).body(service.add(voitureId, url, principale));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
