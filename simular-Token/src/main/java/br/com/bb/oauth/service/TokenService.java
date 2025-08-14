package br.com.bb.oauth.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;

@Service
public class TokenService {

    @Value("${bb.oauth.client-id}")
    private String configuredClientId;

    @Value("${bb.oauth.client-secret}")
    private String configuredClientSecret;

    @Getter
    @Value("${bb.oauth.token-ttl}")
    private long tokenTtl;

    private String token;
    private Instant expiration;

    public boolean isValidToken(String token) {
        return this.token != null && this.token.equals(token) && Instant.now().isBefore(expiration);
    }

    public String generateLargeToken(String clientId, String clientSecret) {
        if (!configuredClientId.equals(clientId) || !configuredClientSecret.equals(clientSecret)) {
            return null;
        }

        if (token == null || Instant.now().isAfter(expiration)) {
            token = gerarTokenGigante();
            expiration = Instant.now().plusSeconds(tokenTtl);
        }

        return token;
    }

    private String gerarTokenGigante() {
        String header = Base64.getUrlEncoder().withoutPadding()
                .encodeToString("{\"alg\":\"HS256\",\"typ\":\"JWT\"}".getBytes());

        String payload = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(("{\"exp\":" + (Instant.now().getEpochSecond() + tokenTtl) + "}")
                        .getBytes());

        byte[] randomBytes = new byte[128]; // aumenta o tamanho
        new SecureRandom().nextBytes(randomBytes);
        String signature = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);

        return header + "." + payload + "." + signature;
    }
}

