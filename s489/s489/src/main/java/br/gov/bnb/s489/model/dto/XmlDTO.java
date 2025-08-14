package br.gov.bnb.s489.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "Dados contidos no elemento ObjectData do XML.")
public class XmlDTO {

    @NotBlank(message = "CodigoCanal é obrigatório")
    @XmlAttribute(name = "CodigoCanal")
    @ApiModelProperty(value = "Código do canal", example = "2", required = true)
    private String codigoCanal;

    @NotBlank(message = "DataLancamento é obrigatória")
    @XmlAttribute(name = "DataLancamento")
    @ApiModelProperty(value = "Data de lançamento", example = "16/12/2013", required = true)
    private String dataLancamento;

    @NotBlank
    @XmlAttribute(name = "FormaPagamento")
    @ApiModelProperty(value = "Forma de pagamento", example = "3", required = true)
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
