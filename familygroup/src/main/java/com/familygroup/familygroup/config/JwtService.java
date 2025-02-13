package com.familygroup.familygroup.config;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@Service
public class JwtService {

    private final JwtEncoder encoder;

    public JwtService(JwtEncoder enconder) {
        this.encoder = enconder;
    }

    public String generateToken(Authentication authentication) {

        Instant instant = Instant.now();
        Long expires = 3600L;

        String scopes = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("family-group")
                .issuedAt(instant)
                .expiresAt(instant.plusSeconds(expires))
                .claim("scope", scopes)
                .build();

        String token = encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return "{\"Authorization\": \"" + token + "\"}";
    }
}
