package br.org.ministerioatos.backend.security;

import jakarta.persistence.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class SupabaseGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt)
    {
        Map<String, Object> userMetadata = jwt.getClaim("user_metadata");

        if (userMetadata == null || !userMetadata.containsKey("role")) {
            return Collections.emptyList();
        }

        String role = (String) userMetadata.get("role");
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
    }
}
