package org.e.filerouge.service.impl;

import lombok.RequiredArgsConstructor;
import org.e.filerouge.entity.Image;
import org.e.filerouge.entity.Voiture;
import org.e.filerouge.exception.ResourceNotFoundException;
import org.e.filerouge.repository.ImageRepository;
import org.e.filerouge.repository.VoitureRepository;
import org.e.filerouge.service.ImageService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository images;
    private final VoitureRepository cars;

    @Override
    @CacheEvict(value = "voitures", key = "#id")
    public Image add(Long id, String url, boolean p) {
        Voiture v = cars.findById(id).orElseThrow(() -> new ResourceNotFoundException("Voiture introuvable"));
        return images.save(new Image(null, url, p, v));
    }

    @Override
    @CacheEvict(value = "voitures", allEntries = true)
    public void delete(Long id) {
        images.deleteById(id);
    }
}
