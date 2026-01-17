package br.org.ministerioatos.backend.security;

import org.springframework.core.convert.converter.Converter; // ERRADO!
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

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
        String authority = "ROLE_" + role;

        return Collections.singletonList(new SimpleGrantedAuthority(authority));
    }
}
