package org.e.filerouge.dto.admin;

public record UserResponse(Long id, String nom, String prenom, String email, String telephone, String role, boolean actif) {

}