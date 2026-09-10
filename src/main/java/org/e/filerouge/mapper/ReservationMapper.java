package org.e.filerouge.mapper;

import org.e.filerouge.dto.auth.reservation.ReservationResponse;
import org.e.filerouge.entity.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public ReservationResponse toResponse(Reservation r) {
        return new ReservationResponse(r.getId(), r.getDateDebut(), r.getDateFin(), r.getMontantTotal(), r.getStatut().name(), r.getClient().getId(), r.getVoiture().getId());
    }
}
