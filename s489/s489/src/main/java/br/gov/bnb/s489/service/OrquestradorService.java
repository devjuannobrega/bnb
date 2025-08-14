package br.gov.bnb.s489.service;

import br.gov.bnb.s489.model.dto.ReceiverDTO;
import br.gov.bnb.s489.model.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class OrquestradorService {

    private final WebClient webClient;
    private final ValidarXmlService validarXmlService;

    public ResponseDTO processar(ReceiverDTO request) {
        validarXmlService.validarXml(request.getXml());
        String url = System.getProperty("orquestrador.url", "http://localhost:8082/teste/wrapper");

        return webClient.post()
                .uri(url)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ResponseDTO.class)
                .block();
    }
}
