package org.e.filerouge.repository;

import org.e.filerouge.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ImageRepository extends JpaRepository<Image, Long> {
}
