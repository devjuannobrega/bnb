package br.gov.bnb.s489.service;

import br.gov.bnb.s489.commons.config.Bb.BbPagamentoService;
import br.gov.bnb.s489.model.dto.ReceiverDTO;
import br.gov.bnb.s489.model.dto.ResponseDTO;
import br.gov.bnb.s489.model.xml.XmlMapper;
import br.gov.bnb.s489.model.xml.XmlMapperReceiver;
import br.gov.bnb.s489.utils.exception.XmlValidationException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.StringReader;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrquestradorService {

    private final WebClient webClient;
    private final ValidarXmlService validarXmlService; // valida ENTRADA
    private final Validator validator;                 // valida SAÍDA (se XmlMapperReceiver tiver anotações)
    private final BbPagamentoService bbPagamentoService;

    @Value("${bnb.wrapper.url}")
    private String wrapperUrl;

    public ResponseDTO processar(ReceiverDTO request) {
        log.info(">> OrquestradorService.processar iniciado para usuario={}", request.getUsuario());

        // 1) Valida XML de ENTRADA
        validarXmlService.validarXml(request.getXml());

        // 2) Chama API externa (wrapper)
        ResponseDTO resposta = webClient.post()
                .uri(wrapperUrl)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, resp ->
                        resp.bodyToMono(String.class)
                                .defaultIfEmpty("Erro desconhecido ao chamar orquestrador")
                                .flatMap(msg -> Mono.error(new RuntimeException("Erro " + resp.statusCode() + ": " + msg)))
                )
                .bodyToMono(ResponseDTO.class)
                .block(Duration.ofSeconds(15));

        if (resposta == null || resposta.getXml() == null || resposta.getXml().isBlank()) {
            throw new XmlValidationException("Resposta da API externa inválida: XML ausente.");
        }

        // 3) Unmarshal do XML de SAÍDA
        XmlMapperReceiver xmlReceiver = unmarshalXmlReceiver(resposta.getXml());

        // 4) (Opcional) Validação do XML de SAÍDA via Bean Validation
        Set<ConstraintViolation<XmlMapperReceiver>> violacoes = validator.validate(xmlReceiver);
        if (!violacoes.isEmpty()) {
            String erros = violacoes.stream()
                    .map(v -> v.getPropertyPath() + " - " + v.getMessage())
                    .collect(Collectors.joining("; "));
            throw new XmlValidationException("XML de SAÍDA inválido: " + erros);
        }

        // 5) Verificar RAJADA e acionar BB quando presente porém vazia
        String rajada = null;
        if (xmlReceiver.getObjectData() != null && xmlReceiver.getObjectData().getRecolhimento() != null) {
            rajada = xmlReceiver.getObjectData().getRecolhimento().getRajada();
        }

        if (rajada == null) {
            log.info("Campo 'Rajada' AUSENTE no XML de saída.");
        } else if (rajada.trim().isEmpty()) {
            log.info("Campo 'Rajada' presente porém VAZIO no XML de saída. Acionando pagamento BB...");

            // Extrai dados para pagamento
            var rec = xmlReceiver.getObjectData().getRecolhimento();

            // valor (BigDecimal, aceita vírgula)
            BigDecimal valorPagamento = parseValorMonetario(rec.getValor());
            if (valorPagamento == null) {
                throw new XmlValidationException("Valor de pagamento inválido ou ausente no XML de saída.");
            }

            String codigoBarrassSDV = rec.getCodigoBarra(); // sem DV (saída)
            if (isBlank(codigoBarrassSDV)) {
                throw new XmlValidationException("Código de barras (sem DV) ausente no XML de saída.");
            }

            // Linha digitável (com DV) vem do XML de ENTRADA
            XmlMapper xmlEntrada = unmarshalXmlEntrada(request.getXml());
            if (xmlEntrada.getObjectData() == null || isBlank(xmlEntrada.getObjectData().getLinhaDigitavel())) {
                throw new XmlValidationException("Linha digitável (com DV) ausente no XML de entrada.");
            }
            String codigoBarrasCDV = xmlEntrada.getObjectData().getLinhaDigitavel();

            // Chama Banco do Brasil
            String respBb = bbPagamentoService.enviarPagamento(
                    valorPagamento.doubleValue(),
                    codigoBarrasCDV,
                    codigoBarrassSDV
            );

            log.info("Retorno BB: {}", respBb);

        } else {
            log.warn("⚠ Campo 'Rajada' encontrado no XML de saída: {}", rajada);
        }

        // Mantém a resposta original do wrapper
        return resposta;
    }

    private XmlMapperReceiver unmarshalXmlReceiver(String xml) {
        try {
            JAXBContext ctx = JAXBContext.newInstance(XmlMapperReceiver.class);
            Unmarshaller u = ctx.createUnmarshaller();
            try (StringReader sr = new StringReader(xml)) {
                return (XmlMapperReceiver) u.unmarshal(sr);
            }
        } catch (Exception e) {
            throw new XmlValidationException("Falha ao interpretar XML de SAÍDA: " + e.getMessage(), e);
        }
    }

    private XmlMapper unmarshalXmlEntrada(String xml) {
        try {
            JAXBContext ctx = JAXBContext.newInstance(XmlMapper.class);
            Unmarshaller u = ctx.createUnmarshaller();
            try (StringReader sr = new StringReader(xml)) {
                return (XmlMapper) u.unmarshal(sr);
            }
        } catch (Exception e) {
            throw new XmlValidationException("Falha ao interpretar XML de ENTRADA: " + e.getMessage(), e);
        }
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private static BigDecimal parseValorMonetario(String valor) {
        if (isBlank(valor)) return null;
        try {
            // aceita "10,54" e "10.54"
            String norm = valor.trim().replace(".", "").replace(",", ".");
            return new BigDecimal(norm);
        } catch (Exception e) {
            return null;
        }
    }
}
