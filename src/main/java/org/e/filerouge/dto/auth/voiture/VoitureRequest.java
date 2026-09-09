package org.e.filerouge.dto.auth.voiture;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record VoitureRequest(@NotBlank
                             String marque, @NotBlank
                             String modele, Integer annee, String immatriculation, String couleur, Integer nombrePlaces, String transmission, @NotNull
                             @Positive
                             BigDecimal prixParJour, Long categorieId, Long villeId) {

}
