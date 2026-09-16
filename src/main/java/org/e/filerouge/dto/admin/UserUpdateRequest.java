package org.e.filerouge.dto.admin;

import jakarta.validation.constraints.*;

public record UserUpdateRequest(@Size(max = 255)
                                String nom, @Size(max = 255)
                                String prenom, @Size(max = 50)
                                String telephone, Boolean actif) {

}