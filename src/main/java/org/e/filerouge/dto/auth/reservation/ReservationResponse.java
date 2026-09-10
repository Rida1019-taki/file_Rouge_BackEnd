package org.e.filerouge.dto.auth.reservation;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReservationResponse(Long id, LocalDate dateDebut, LocalDate dateFin, BigDecimal montantTotal, String statut, Long clientId, Long voitureId) {

}
