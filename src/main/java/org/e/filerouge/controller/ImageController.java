package org.e.filerouge.controller;

import lombok.RequiredArgsConstructor;
import org.e.filerouge.entity.Image;
import org.e.filerouge.service.FileStorageService;
import org.e.filerouge.service.ImageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService service;
    private final FileStorageService storage;

    @PostMapping("/voiture/{voitureId}")
    public ResponseEntity<Image> add(@PathVariable Long voitureId, @RequestParam String url, @RequestParam(defaultValue = "false") boolean principale) {
        return ResponseEntity.status(201).body(service.add(voitureId, url, principale));
    }

    @PostMapping(value = "/voiture/{voitureId}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Image> upload(@PathVariable Long voitureId, @RequestParam("file") MultipartFile file, @RequestParam(defaultValue = "false") boolean principale) {
        String path = storage.store(file);
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path(path).toUriString();
        return ResponseEntity.status(201).body(service.add(voitureId, url, principale));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
