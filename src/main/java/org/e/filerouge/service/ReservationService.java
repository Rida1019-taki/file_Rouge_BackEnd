package org.e.filerouge.service;

import org.e.filerouge.dto.auth.reservation.ReservationRequest;
import org.e.filerouge.dto.auth.reservation.ReservationResponse;

import java.util.List;

public interface ReservationService {

    ReservationResponse create(ReservationRequest r, Long clientId);

    List<ReservationResponse> findMyReservations(Long clientId);

    List<ReservationResponse> findOwnerReservations(Long ownerId);

    ReservationResponse updateStatus(Long id, String status);
}
