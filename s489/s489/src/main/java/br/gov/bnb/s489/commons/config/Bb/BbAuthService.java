package br.gov.bnb.s489.commons.config.Bb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Service
@Slf4j
public class BbAuthService {

    @Value("${bb.api.client.tokenUrl}")
    private String tokenUrl;

    @Value("${bb.api.client.clientId}")
    private String clientId;

    @Value("${bb.api.client.secretId}")
    private String secretId;

    public String obterToken() {
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("client_id", clientId);
            body.add("client_secret", secretId);
            body.add("grant_type", "client_credentials");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                String token = (String) response.getBody().get("access_token");
                log.info("Token BB obtido com sucesso.");
                return token;
            } else {
                log.error("Falha ao obter token. Status: {}", response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Erro ao obter token do Banco do Brasil", e);
        }
        return null;
    }

}
