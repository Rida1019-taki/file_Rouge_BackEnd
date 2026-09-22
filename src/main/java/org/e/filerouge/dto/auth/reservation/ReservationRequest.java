package org.e.filerouge.dto.auth.reservation;

import jakarta.validation.constraints.*;
import org.e.filerouge.enums.TypeReservation;

import java.time.LocalDate;

public record ReservationRequest(@NotNull
                                 Long voitureId,
                                 TypeReservation type,
                                 LocalDate dateDebut,
                                 LocalDate dateFin) {

}