package org.e.filerouge.repository;

import org.e.filerouge.entity.Reservation;
import org.e.filerouge.enums.StatutReservation;
import org.e.filerouge.enums.TypeReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByClientId(Long clientId);

    List<Reservation> findByVoitureOwnerId(Long ownerId);

    boolean existsByVoitureIdAndTypeAndStatutIn(Long voitureId, TypeReservation type, Collection<StatutReservation> statuts);
}