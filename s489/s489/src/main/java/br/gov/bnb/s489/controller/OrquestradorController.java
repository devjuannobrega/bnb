package br.gov.bnb.s489.controller;

import br.gov.bnb.s489.model.dto.ReceiverDTO;
import br.gov.bnb.s489.model.dto.ResponseDTO;
import br.gov.bnb.s489.service.OrquestradorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/br/gov/bnb/s489")
@RequiredArgsConstructor
@Slf4j
public class OrquestradorController {

    private final OrquestradorService orquestradorService;

    @PostMapping("/orquestrador")
    public ResponseEntity<String> receber(@RequestBody ReceiverDTO request) {
        log.info(">> Controller: recebida requisição /orquestrador para usuario={}", request.getUsuario());
        String respostaJson = orquestradorService.processar(request);
        return ResponseEntity.ok(respostaJson);
    }
}

