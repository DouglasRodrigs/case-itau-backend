package com.example.demo.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JWTConverterTest {

    @InjectMocks
    private JWTConverter jwtConverter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConvert() {
        // Mock JWT
        Jwt jwt = mock(Jwt.class);
        Map<String, Collection<String>> realmAccess = Map.of("roles", Collections.singletonList("USER"));
        when(jwt.getClaim("realm_access")).thenReturn(realmAccess);

        // Call the method under test
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) jwtConverter.convert(jwt);

        // Assertions
        assertEquals(1, authenticationToken.getAuthorities().size());
        assertEquals(new SimpleGrantedAuthority("ROLE_USER"), authenticationToken.getAuthorities().iterator().next());
        assertEquals(jwt, authenticationToken.getToken());
    }

    @Test
    public void testConvertWithMultipleRoles() {
        // Mock JWT
        Jwt jwt = mock(Jwt.class);
        Map<String, Collection<String>> realmAccess = Map.of("roles", List.of("USER", "ADMIN"));
        when(jwt.getClaim("realm_access")).thenReturn(realmAccess);

        // Call the method under test
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) jwtConverter.convert(jwt);

        // Assertions
        assertEquals(2, authenticationToken.getAuthorities().size());
        assertEquals(Set.of(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN")),
                new HashSet<>(authenticationToken.getAuthorities()));
        assertEquals(jwt, authenticationToken.getToken());
    }

    @Test
    public void testConvertWithNoRoles() {
        // Mock JWT
        Jwt jwt = mock(Jwt.class);
        Map<String, Collection<String>> realmAccess = Map.of("roles", Collections.emptyList());
        when(jwt.getClaim("realm_access")).thenReturn(realmAccess);

        // Call the method under test
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) jwtConverter.convert(jwt);

        // Assertions
        assertEquals(0, authenticationToken.getAuthorities().size());
        assertEquals(jwt, authenticationToken.getToken());
    }
}
