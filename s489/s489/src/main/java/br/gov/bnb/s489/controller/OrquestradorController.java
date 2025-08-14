package br.gov.bnb.s489.controller;

import br.gov.bnb.s489.model.dto.ReceiverDTO;
import br.gov.bnb.s489.service.ValidarXmlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/br/gov/bnb/s489")
@RequiredArgsConstructor
public class OrquestradorController {

    private final ValidarXmlService validarXmlService;

    @PostMapping("/orquestrador")
    public ResponseEntity<String> receber(@RequestBody ReceiverDTO request) {
        String resposta = validarXmlService.validarXml(request.getXml());
        return ResponseEntity.ok(resposta);
    }
}
