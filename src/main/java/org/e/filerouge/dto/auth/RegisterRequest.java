package org.e.filerouge.dto.auth;

import jakarta.validation.constraints.*;


public record RegisterRequest(@NotBlank
                              String nom, @NotBlank
                              String prenom, @Email
                              @NotBlank
                              String email, @NotBlank
                              String telephone, @NotBlank
                              String password, @NotNull
                              Role role) {

}
