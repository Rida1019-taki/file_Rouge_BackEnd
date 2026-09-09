package org.e.filerouge.mapper;

import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public ReservationResponse toResponse(Reservation r) {
        return new ReservationResponse(r.getId(), r.getDateDebut(), r.getDateFin(), r.getMontantTotal(), r.getStatut().name(), r.getClient().getId(), r.getVoiture().getId());
    }
}
