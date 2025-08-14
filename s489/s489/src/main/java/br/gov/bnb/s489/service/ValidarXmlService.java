package br.gov.bnb.s489.service;

import br.gov.bnb.s489.commons.config.Bb.BbPagamentoService;
import br.gov.bnb.s489.model.xml.XmlMapper;
import br.gov.bnb.s489.model.xml.XmlMapperReceiver;
import br.gov.bnb.s489.utils.exception.XmlValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ValidarXmlService {

    private final Validator validator;
    private final BbPagamentoService bancoBrasilPagamentoService;

    public String validarXml(String xmlString) {
        try {
            // 1️⃣ Validação de campos obrigatórios usando XmlMapper
            JAXBContext contextMapper = JAXBContext.newInstance(XmlMapper.class);
            Unmarshaller unmarshallerMapper = contextMapper.createUnmarshaller();
            XmlMapper xmlMapper = (XmlMapper) unmarshallerMapper.unmarshal(new StringReader(xmlString));

            Set<ConstraintViolation<XmlMapper>> violacoes = validator.validate(xmlMapper);

            if (!violacoes.isEmpty()) {
                String erros = violacoes.stream()
                        .map(v -> v.getPropertyPath() + " - " + v.getMessage())
                        .collect(Collectors.joining("; "));
                throw new XmlValidationException("Campos obrigatórios ausentes ou nulos: " + erros);
            }

            JAXBContext contextReceiver = JAXBContext.newInstance(XmlMapperReceiver.class);
            Unmarshaller unmarshallerReceiver = contextReceiver.createUnmarshaller();
            XmlMapperReceiver xmlReceiver = (XmlMapperReceiver) unmarshallerReceiver.unmarshal(new StringReader(xmlString));

            String rajada = null;
            if (xmlReceiver.getObjectData() != null &&
                    xmlReceiver.getObjectData().getRecolhimento() != null) {
                rajada = xmlReceiver.getObjectData().getRecolhimento().getRajada();
            }

            if (rajada == null || rajada.trim().isEmpty()) {
                double valorPagamento = Double.parseDouble(
                        xmlReceiver.getObjectData().getRecolhimento().getValor().replace(",", ".")
                );
                String codigoBarrasCDV = xmlMapper.getObjectData().getLinhaDigitavel(); // vem do XML original
                String codigoBarrassSDV = xmlReceiver.getObjectData().getRecolhimento().getCodigoBarra();

                return bancoBrasilPagamentoService.enviarPagamento(
                        valorPagamento,
                        codigoBarrasCDV,
                        codigoBarrassSDV
                );
            }

            return "{\"status\":\"ignorado\",\"motivo\":\"Rajada preenchida\"}";

        } catch (XmlValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new XmlValidationException("Erro ao processar XML: " + e.getMessage(), e);
        }
    }
}
