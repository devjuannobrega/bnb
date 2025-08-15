package br.gov.bnb.s489.commons.config.Bb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class BbPagamentoService {

    private final BbAuthService authService;

    @Value("${bb.api.client.pagamentoUrl}")
    private String pagamentoUrl;

    @Value("${bb.api.client.gw-app-key}")
    private String gwAppKey;

    public String enviarPagamento(double valorPagamento, String codigoBarrasCDV, String codigoBarrassSDV) {
        try {
            String token = authService.obterToken();
            if (token == null) {
                throw new RuntimeException("Token BB não obtido.");
            }

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + token);
            headers.set("gw-app-key", gwAppKey);

            PagamentoRequest pagamento = new PagamentoRequest(valorPagamento, codigoBarrasCDV, codigoBarrassSDV, "20");

            HttpEntity<PagamentoRequest> entity = new HttpEntity<>(pagamento, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    pagamentoUrl,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            return response.getBody();

        } catch (Exception e) {
            log.error("Erro ao enviar pagamento para o Banco do Brasil", e);
            return null;
        }
    }

    @Data
    @AllArgsConstructor
    public static class PagamentoRequest {
        private double valorPagamento;

        @JsonProperty("codigoBarras-c-dv")
        private String codigoBarrasCDV;

        @JsonProperty("codigoBarrass-s-dv")
        private String codigoBarrassSDV;

        private String codigoConvenio;
    }

}
