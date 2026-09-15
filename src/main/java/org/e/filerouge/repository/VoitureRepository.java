package org.e.filerouge.repository;

import org.e.filerouge.entity.Voiture;
import org.e.filerouge.enums.ListingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VoitureRepository extends JpaRepository<Voiture, Long> {

    @Query("select distinct v from Voiture v "
            + "left join fetch v.categorie "
            + "left join fetch v.ville "
            + "left join fetch v.images "
            + "left join fetch v.owner o "
            + "left join fetch o.utilisateur "
            + "where v.owner.id = :ownerId")
    List<Voiture> findByOwnerId(@Param("ownerId") Long ownerId);

    @Query("select distinct v from Voiture v "
            + "left join fetch v.categorie "
            + "left join fetch v.ville "
            + "left join fetch v.images "
            + "left join fetch v.owner o "
            + "left join fetch o.utilisateur "
            + "where v.ville.id = :villeId and v.categorie.id = :categorieId")
    List<Voiture> findByVilleIdAndCategorieId(@Param("villeId") Long villeId, @Param("categorieId") Long categorieId);

    @Query("select distinct v from Voiture v "
            + "left join fetch v.categorie "
            + "left join fetch v.ville "
            + "left join fetch v.images "
            + "left join fetch v.owner o "
            + "left join fetch o.utilisateur "
            + "where v.listingType = :type")
    List<Voiture> findByListingType(@Param("type") ListingType type);

    long countByListingType(ListingType type);

    @Query("select distinct v from Voiture v "
            + "left join fetch v.categorie "
            + "left join fetch v.ville "
            + "left join fetch v.images "
            + "left join fetch v.owner o "
            + "left join fetch o.utilisateur ")
    @Override
    List<Voiture> findAll();

    @Query("select distinct v from Voiture v "
            + "left join fetch v.categorie "
            + "left join fetch v.ville "
            + "left join fetch v.images "
            + "left join fetch v.owner o "
            + "left join fetch o.utilisateur "
            + "where v.id = :id")
    @Override
    Optional<Voiture> findById(@Param("id") Long id);
}