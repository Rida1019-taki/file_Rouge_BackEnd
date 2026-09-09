package org.e.filerouge.dto.auth;

public record AuthResponse(String token, Long userId, String nom, String prenom, String email, String role) {

}
