package org.e.filerouge.dto.auth.voiture;

import jakarta.validation.constraints.*;
import org.e.filerouge.enums.ListingType;

import java.math.BigDecimal;

public record VoitureRequest(@NotBlank
                             String marque, @NotBlank
                             String modele, Integer annee, String immatriculation, String couleur, Integer nombrePlaces, String transmission, @Positive
                             BigDecimal prixParJour, BigDecimal prixVente, ListingType listingType, Long categorieId, Long villeId) {

}