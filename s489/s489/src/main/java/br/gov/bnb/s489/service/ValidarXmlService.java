package br.gov.bnb.s489.service;

import br.gov.bnb.s489.model.xml.XmlMapper;
import br.gov.bnb.s489.utils.exception.XmlValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ValidarXmlService {

    private final Validator validator;

    public void validarXml(String xmlString) {
        try {
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

            log.debug("XML de ENTRADA validado com sucesso.");

        } catch (XmlValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new XmlValidationException("Erro ao processar XML de ENTRADA: " + e.getMessage(), e);
        }
    }
}
