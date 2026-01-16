package br.org.ministerioatos.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

public class SupabaseAuditorAware implements AuditorAware<UUID> {

    @Override
    public Optional<UUID> getCurrentAuditor() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        if (authentication instanceof JwtAuthenticationToken jwtAuthToken) {
            Jwt jwt = jwtAuthToken.getToken();
            String sub = jwt.getSubject();
            return Optional.of(UUID.fromString(sub));
        }

        return Optional.empty();
    }
}
