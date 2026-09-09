package org.e.filerouge.dto.auth.reservation;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record ReservationRequest(@NotNull
                                 Long voitureId, @NotNull
                                 @FutureOrPresent
                                 LocalDate dateDebut, @NotNull
                                 LocalDate dateFin) {

}
