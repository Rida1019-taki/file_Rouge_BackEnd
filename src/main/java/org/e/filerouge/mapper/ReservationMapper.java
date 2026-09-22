package org.e.filerouge.mapper;

import org.e.filerouge.dto.auth.reservation.ReservationResponse;
import org.e.filerouge.entity.Client;
import org.e.filerouge.entity.Image;
import org.e.filerouge.entity.Reservation;
import org.e.filerouge.entity.Voiture;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservationMapper {

    public ReservationResponse toResponse(Reservation r) {
        Voiture v = r.getVoiture();
        Client c = r.getClient();
        List<String> images = v.getImages() == null
                ? List.of()
                : v.getImages().stream().map(Image::getUrl).toList();
        return new ReservationResponse(
                r.getId(),
                r.getType() == null ? "LOCATION" : r.getType().name(),
                r.getDateDebut(),
                r.getDateFin(),
                r.getMontantTotal(),
                r.getStatut().name(),
                c.getId(),
                c.getUtilisateur() != null ? c.getUtilisateur().getNom() : null,
                c.getUtilisateur() != null ? c.getUtilisateur().getPrenom() : null,
                v.getId(),
                v.getMarque(),
                v.getModele(),
                images);
    }
}