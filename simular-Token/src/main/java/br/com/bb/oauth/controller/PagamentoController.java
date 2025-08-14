package br.com.bb.oauth.controller;

import br.com.bb.oauth.service.TokenService;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
public class PagamentoController {

    private final TokenService tokenService;

    @Value("${bb.oauth.gw-app-key}")
    private String gwAppKeyConfig;

    private static final Pattern REGEX_48 = Pattern.compile("^\\d{48}$");
    private static final Pattern REGEX_44 = Pattern.compile("^\\d{44}$");

    public PagamentoController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/pagamento")
    public ResponseEntity<String> processarPagamento(
            @RequestHeader("Authorization") String authorization,
            @RequestHeader("gw-app-key") String gwAppKey,
            @RequestBody PagamentoRequest request) {

        String token = authorization.replace("Bearer", "").trim();
        if (!tokenService.isValidToken(token)) {
            return ResponseEntity.status(401).body(jsonError("invalid_token"));
        }

        if (!gwAppKeyConfig.equals(gwAppKey)) {
            return ResponseEntity.status(403).body(jsonError("invalid_gw_app_key"));
        }

        if (!isValidRequest(request)) {
            return ResponseEntity.badRequest().body(jsonError("invalid_request_format"));
        }

        String codigoConciliacao = "bnb.gov.br-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12);
        String qrCodePix = gerarPixFicticio(request.getValorPagamento());

        return ResponseEntity.ok(
                "{\"codigoConciliacao\":\"" + codigoConciliacao + "\"," +
                        "\"textoQRCode\":\"" + qrCodePix + "\"}"
        );
    }

    private boolean isValidRequest(PagamentoRequest request) {
        if (request == null) return false;
        if (request.getValorPagamento() <= 0) return false;
        if (request.getCodigoConvenio() == null || request.getCodigoConvenio().isBlank()) return false;
        if (request.getCodigoBarrasCDV() == null || !REGEX_48.matcher(request.getCodigoBarrasCDV()).matches()) return false;
        if (request.getCodigoBarrassSDV() == null || !REGEX_44.matcher(request.getCodigoBarrassSDV()).matches()) return false;
        return true;
    }

    private String gerarPixFicticio(double valor) {
        return "00020126580014BR.GOV.BCB.PIX0136bnb.gov@teste.com.br52040000530398654"
                + String.format("%010.2f", valor).replace(",", "")
                + "5802BR5920LOJA TESTE LTDA6009SAO PAULO62070503***6304ABCD";
    }

    private String jsonError(String message) {
        return "{\"error\":\"" + message + "\"}";
    }

    @Data
    public static class PagamentoRequest {
        @JsonProperty("valorPagamento")
        private double valorPagamento;

        @JsonProperty("codigoBarras-c-dv")
        private String codigoBarrasCDV;

        @JsonProperty("codigoBarrass-s-dv")
        private String codigoBarrassSDV;

        @JsonProperty("codigoConvenio")
        private String codigoConvenio;
    }
}
