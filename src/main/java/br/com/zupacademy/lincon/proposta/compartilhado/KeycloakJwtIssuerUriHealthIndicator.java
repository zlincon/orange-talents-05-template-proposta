package br.com.zupacademy.lincon.proposta.compartilhado;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.net.Socket;

@Component("Keycloack Jwt-Issuer-Uri")
public class KeycloakJwtIssuerUriHealthIndicator implements HealthIndicator, HealthContributor {

        @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
        private String url;
        @Value("${keycloak.jwt.issuer-uri.port}")
        private Integer port;

    @Override
    public Health health() {
        try (Socket socket =
                     new Socket(new java.net.URL(url).getHost(),
                             port)) {
        } catch (Exception e) {
            return Health.down().withDetail("error", e.getMessage()).build();
        }
        return Health.up().build();
    }
}
