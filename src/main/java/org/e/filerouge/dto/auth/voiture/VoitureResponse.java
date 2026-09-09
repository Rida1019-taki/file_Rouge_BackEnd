package org.e.filerouge.dto.auth.voiture;

import java.util.List;

import java.math.BigDecimal;
import java.util.List;

public record VoitureResponse(Long id, String marque, String modele, Integer annee, String immatriculation, String couleur, Integer nombrePlaces, String transmission, BigDecimal prixParJour, String statut, Long ownerId, Long categorieId, String categorie, Long villeId, String ville, List<String> images) {

}
