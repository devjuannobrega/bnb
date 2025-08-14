package br.gov.bnb.controller;

import br.gov.bnb.model.dto.ReceiverDTO;
import br.gov.bnb.model.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @PostMapping("/wrapper")
    public ResponseEntity<ResponseDTO.Result> receber(@RequestBody ReceiverDTO receiverDTO) {
        ResponseDTO.Result result = new ResponseDTO.Result();
        result.setUsuario("CONTROLM");
        result.setXml("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\r\n"
                + "<SYSMSG><ObjectData NewTarget=\"True\">"
                + "<Operacoes><ngOperation Description=\"Excluir\" Code=\"4\" Situation=\"A\" OID=\"3\"></ngOperation>"
                + "<ngOperation Description=\"Atualizar\" Code=\"3\" Situation=\"A\" OID=\"4\"></ngOperation>"
                + "<ngOperation Description=\"Consultar\" Code=\"2\" Situation=\"A\" OID=\"5\"></ngOperation>"
                + "<ngOperation Description=\"Consultar\" Code=\"2\" Situation=\"A\" OID=\"5\"></ngOperation>"
                + "<ngOperation Description=\"Consultar\" Code=\"2\" Situation=\"A\" OID=\"5\"></ngOperation>"
                + "<ngOperation Description=\"Inserir\" Code=\"1\" Situation=\"A\" OID=\"6\"></ngOperation></Operacoes>"
                + "<Recolhimento CodigoBarra=\"82640000000105415350000020221505657520100104\" DataMovimento=\"04/08/2025\" Valor=\"10,54\" DataLancamento=\"04/08/2025\" Processado=\"N\" PrestadoConta=\"N\" DataPagamento=\"04/08/2025\" DigitoContaCorrenteCliente=\"1\" NumeroContaCorrenteCliente=\"3874\" DataRegistro=\"04/08/2025 11:13:04 \" DataRegistroPagamento=\"04/08/2025 11:13:06 \" Rajada=\"S\" CodigoIdentificador=\"82640000000105415350000020221505657520100104\" DataPrestacaoConta=\"05/08/2025\" OID=\"\" NumeroAgenciaCliente=\"0016\">"
                + "<Canal Descricao=\"Nordeste Eletronico\" Datainivalidade=\"18/03/2009\" usuarioatualizador=\"C015973\" dataultatualizacao=\"26/03/2021 12:00:45 \" rotinaatualizadora=\"ucCadastroCanal\" Codigo=\"5\" usuariologado=\"CONTROLM\" ExigeInformacaoAutenticacao=\"N\" DebitoAutomatico=\"N\" AutoAtendimento=\"S\" ValidaFeriadoLocal=\"S\" OID=\"5\"></Canal>"
                + "</Recolhimento></ObjectData></SYSMSG>");

        return ResponseEntity.ok(result);
    }
}
