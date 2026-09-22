package org.e.filerouge.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.e.filerouge.dto.auth.voiture.VoitureRequest;
import org.e.filerouge.dto.auth.voiture.VoitureResponse;
import org.e.filerouge.entity.Utilisateur;
import org.e.filerouge.enums.ListingType;
import org.e.filerouge.exception.BadRequestException;
import org.e.filerouge.service.FileStorageService;
import org.e.filerouge.service.ImageService;
import org.e.filerouge.service.VoitureService;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/voitures")
@RequiredArgsConstructor
public class VoitureController {

    private final VoitureService service;
    private final ImageService imageService;
    private final FileStorageService storage;

    @GetMapping
    public Object all(@RequestParam(required = false) ListingType listingType) {
        return listingType == null ? service.findAll() : service.findByListingType(listingType);
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/mine")
    public List<VoitureResponse> mine(Authentication a) {
        return service.findMine(((Utilisateur) a.getPrincipal()).getId());
    }

    @GetMapping("/{id}")
    public VoitureResponse one(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<VoitureResponse> create(@Valid @RequestBody VoitureRequest r, Authentication a) {
        return ResponseEntity.status(201).body(service.create(r, ((Utilisateur) a.getPrincipal()).getId()));
    }

    @PostMapping(value = "/{id}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<String>> addImages(
            @PathVariable Long id,
            @RequestParam(value = "files", required = false) List<MultipartFile> files,
            @RequestParam(value = "images", required = false) List<MultipartFile> images,
            @RequestParam(value = "file", required = false) List<MultipartFile> file,
            @RequestParam(value = "principale", required = false, defaultValue = "false") boolean principale) {
        List<MultipartFile> all = new ArrayList<>();
        if (files != null) all.addAll(files);
        if (images != null) all.addAll(images);
        if (file != null) all.addAll(file);
        all.removeIf(f -> f == null || f.isEmpty());
        if (all.isEmpty()) {
            throw new BadRequestException("Aucun fichier image fourni");
        }
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < all.size(); i++) {
            String path = storage.store(all.get(i));
            String url = ServletUriComponentsBuilder.fromCurrentContextPath().path(path).toUriString();
            imageService.add(id, url, principale && i == 0);
            urls.add(url);
        }
        return ResponseEntity.status(201).body(urls);
    }

    @PostMapping(value = "/{id}/images", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addImageByUrl(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Object url = body.get("url");
        if (url == null || String.valueOf(url).isBlank()) {
            throw new BadRequestException("URL de l'image manquante");
        }
        Object principale = body.get("principale");
        imageService.add(id, String.valueOf(url), Boolean.TRUE.equals(principale) || "true".equalsIgnoreCase(String.valueOf(principale)));
        return ResponseEntity.status(201).body(String.valueOf(url));
    }

    @PutMapping("/{id}")
    public VoitureResponse update(@PathVariable Long id, @Valid @RequestBody VoitureRequest r, Authentication a) {
        return service.update(id, r, ((Utilisateur) a.getPrincipal()).getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication a) {
        Utilisateur u = (Utilisateur) a.getPrincipal();
        service.delete(id, u.getId(), u.getRole());
        return ResponseEntity.noContent().build();
    }
}
