package org.e.filerouge.repository;

import org.e.filerouge.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    @Query("select o from Owner o join fetch o.utilisateur where o.id = :id")
    @Override
    Optional<Owner> findById(@Param("id") Long id);
}