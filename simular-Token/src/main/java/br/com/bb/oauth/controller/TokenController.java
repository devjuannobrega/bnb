package br.com.bb.oauth.controller;

import br.com.bb.oauth.service.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth")
public class TokenController {

    private final TokenService tokenService;


    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> getToken(
            @RequestParam("grant_type") String grantType,
            @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret) {

        if (!"client_credentials".equals(grantType)) {
            return ResponseEntity.badRequest().body("{\"error\":\"unsupported_grant_type\"}");
        }

        String token = tokenService.generateLargeToken(clientId, clientSecret);
        if (token == null) {
            return ResponseEntity.status(401).body("{\"error\":\"invalid_client\"}");
        }

        return ResponseEntity.ok("{\"access_token\":\"" + token + "\",\"token_type\":\"Bearer\",\"expires_in\":" + tokenService.getTokenTtl() + "}");
    }

}
