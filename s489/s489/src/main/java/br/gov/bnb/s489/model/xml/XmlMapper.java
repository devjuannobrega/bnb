package br.gov.bnb.s489.model.xml;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlRootElement(name = "SYSMSG")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlMapper {

    @Valid
    @XmlElement(name = "ObjectData", required = true)
    private ObjectData objectData;

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ObjectData {

        @NotBlank
        @XmlAttribute(name = "CodigoCanal")
        private String codigoCanal;

        @NotBlank
        @XmlAttribute(name = "DataLancamento")
        private String dataLancamento;

        @NotBlank
        @XmlAttribute(name = "FormaPagamento")
        private String formaPagamento;

        @NotBlank
        @XmlAttribute(name = "NumeroAgenciaCliente")
        private String numeroAgenciaCliente;

        @NotBlank
        @XmlAttribute(name = "NumeroContaCorrenteCliente")
        private String numeroContaCorrenteCliente;

        @NotBlank
        @XmlAttribute(name = "DigitoContaCorrenteCliente")
        private String digitoContaCorrenteCliente;

        @NotBlank
        @XmlAttribute(name = "TipoConta")
        private String tipoConta;

        @NotBlank
        @XmlAttribute(name = "LinhaDigitavel")
        private String linhaDigitavel;

        @XmlAttribute(name = "CodigoBarra")
        private String codigoBarra;

        @NotBlank
        @XmlAttribute(name = "CodigoUnidadeRecolhedora")
        private String codigoUnidadeRecolhedora;

        @XmlAttribute(name = "Valor")
        private String valor;
    }
}
