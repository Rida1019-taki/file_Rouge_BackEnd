package org.e.filerouge.repository;

import org.e.filerouge.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByClientId(Long clientId);

    List<Reservation> findByVoitureOwnerId(Long ownerId);
}
