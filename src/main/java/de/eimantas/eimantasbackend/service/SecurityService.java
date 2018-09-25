package de.eimantas.eimantasbackend.service;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {


    public String getUserIdFromPrincipal(KeycloakAuthenticationToken keycloakAuthenticationToken) {

        if (keycloakAuthenticationToken == null)
            throw new SecurityException("Principal cannot be null");

        KeycloakPrincipal principal = (KeycloakPrincipal) keycloakAuthenticationToken.getPrincipal();
        RefreshableKeycloakSecurityContext ctx = (RefreshableKeycloakSecurityContext) principal.getKeycloakSecurityContext();
        return ctx.getToken().getSubject();

    }
}
