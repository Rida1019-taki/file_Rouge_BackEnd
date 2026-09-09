package org.e.filerouge.service;

import org.e.filerouge.entity.Image;

public interface ImageService {

    Image add(Long voitureId, String url, boolean principale);

    void delete(Long id);
}
