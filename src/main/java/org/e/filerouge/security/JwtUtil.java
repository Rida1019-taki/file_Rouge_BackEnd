package org.e.filerouge.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey key() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserDetails user) {
        Date now = new Date();
        return Jwts.builder().subject(user.getUsername()).issuedAt(now).expiration(new Date(now.getTime() + expiration)).signWith(key()).compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean isValid(String token, UserDetails user) {
        try {
            return extractUsername(token).equals(user.getUsername()) && Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload().getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
