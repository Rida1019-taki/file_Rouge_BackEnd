package org.e.filerouge.security;

import lombok.RequiredArgsConstructor;
import org.e.filerouge.repository.UtilisateurRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository repo;

    @Override
    @Cacheable(value = "users_by_email", key = "#email")
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable"));
    }
}
