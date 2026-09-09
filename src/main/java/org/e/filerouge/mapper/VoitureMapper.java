package org.e.filerouge.mapper;

import org.e.filerouge.dto.auth.voiture.VoitureResponse;
import org.e.filerouge.entity.Voiture;
import org.springframework.stereotype.Component;

@Component
public class VoitureMapper {

    public VoitureResponse toResponse(Voiture v) {
        return new VoitureResponse(v.getId(), v.getMarque(), v.getModele(), v.getAnnee(), v.getImmatriculation(), v.getCouleur(), v.getNombrePlaces(), v.getTransmission(), v.getPrixParJour(), v.getStatut().name(), v.getOwner().getId(), v.getCategorie().getId(), v.getCategorie().getNom(), v.getVille().getId(), v.getVille().getNom(), v.getImages().stream().map(i -> i.getUrl()).toList());
    }
}
