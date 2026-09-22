package org.e.filerouge.dto.auth.reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ReservationResponse(Long id, String type, LocalDate dateDebut, LocalDate dateFin,
                                  BigDecimal montantTotal, String statut,
                                  Long clientId, String clientNom, String clientPrenom,
                                  Long voitureId, String voitureMarque, String voitureModele,
                                  List<String> voitureImages) {

}