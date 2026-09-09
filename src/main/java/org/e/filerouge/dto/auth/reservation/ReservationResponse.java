package org.e.filerouge.dto.auth.reservation;

public record ReservationResponse(Long id, LocalDate dateDebut, LocalDate dateFin, BigDecimal montantTotal, String statut, Long clientId, Long voitureId) {

}
