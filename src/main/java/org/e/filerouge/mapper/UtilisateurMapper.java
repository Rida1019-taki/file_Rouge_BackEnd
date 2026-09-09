package org.e.filerouge.mapper;

import org.springframework.stereotype.Component;

@Component
public class UtilisateurMapper {

    public ClientResponse toClient(Utilisateur u) {
        return new ClientResponse(u.getId(), u.getNom(), u.getPrenom(), u.getEmail(), u.getTelephone());
    }
}
