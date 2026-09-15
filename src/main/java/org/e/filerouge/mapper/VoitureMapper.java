package org.e.filerouge.mapper;

import org.e.filerouge.dto.auth.voiture.VoitureResponse;
import org.e.filerouge.entity.Owner;
import org.e.filerouge.entity.Utilisateur;
import org.e.filerouge.entity.Voiture;
import org.springframework.stereotype.Component;

@Component
public class VoitureMapper {

    public VoitureResponse toResponse(Voiture v) {
        return new VoitureResponse(v.getId(), v.getMarque(), v.getModele(), v.getAnnee(), v.getImmatriculation(), v.getCouleur(), v.getNombrePlaces(), v.getTransmission(), v.getPrixParJour(), v.getPrixVente(), v.getListingType().name(), v.getStatut().name(), v.getOwner().getId(), ownerEmail(v), ownerPhone(v), v.getCategorie().getId(), v.getCategorie().getNom(), v.getVille().getId(), v.getVille().getNom(), v.getImages().stream().map(i -> i.getUrl()).toList());
    }

    private Utilisateur ownerUser(Voiture v) {
        Owner o = v.getOwner();
        return o == null ? null : o.getUtilisateur();
    }

    private String ownerEmail(Voiture v) {
        Utilisateur u = ownerUser(v);
        return u == null ? null : u.getEmail();
    }

    private String ownerPhone(Voiture v) {
        Utilisateur u = ownerUser(v);
        return u == null ? null : u.getTelephone();
    }
}