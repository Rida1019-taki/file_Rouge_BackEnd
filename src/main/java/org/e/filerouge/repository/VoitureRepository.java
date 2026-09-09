package org.e.filerouge.repository;

import org.e.filerouge.entity.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface VoitureRepository extends JpaRepository<Voiture, Long> {

    List<Voiture> findByOwnerId(Long ownerId);

    List<Voiture> findByVilleIdAndCategorieId(Long villeId, Long categorieId);
}
