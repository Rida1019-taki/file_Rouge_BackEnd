package org.e.filerouge.repository;

import org.e.filerouge.entity.Voiture;
import org.e.filerouge.enums.ListingType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoitureRepository extends JpaRepository<Voiture, Long> {

    List<Voiture> findByOwnerId(Long ownerId);

    List<Voiture> findByVilleIdAndCategorieId(Long villeId, Long categorieId);

    List<Voiture> findByListingType(ListingType type);

    long countByListingType(ListingType type);
}