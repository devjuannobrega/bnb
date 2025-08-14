package br.gov.bnb.s489.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@ApiModel(description = "Requisição do Orquestrador contendo o XML bruto e o usuário.")
public class ReceiverDTO {

    @NotBlank(message = "O campo xml é obrigatório.")
    @ApiModelProperty(
            value = "Conteúdo XML bruto",
            example = "<SYSMSG><ObjectData CodigoCanal=\"2\" DataLancamento=\"16/12/2013\" ... ></ObjectData></SYSMSG>",
            required = true
    )
    private String xml;

    @NotBlank(message = "O campo usuario é obrigatório.")
    @ApiModelProperty(value = "Usuário que está enviando a requisição", example = "CONTROLM", required = true)
    private String usuario;

    @Data
    @ApiModel(description = "Campos mapeados a partir do XML.")
    public static class XmlObjectData {

        @NotBlank(message = "CodigoCanal é obrigatório.")
        @ApiModelProperty(value = "Código do canal", example = "2", required = true)
        private String codigoCanal;

        @NotBlank(message = "DataLancamento é obrigatório.")
        @ApiModelProperty(value = "Data de lançamento", example = "16/12/2013", required = true)
        private String dataLancamento;

        @NotBlank(message = "FormaPagamento é obrigatório.")
        @ApiModelProperty(value = "Forma de pagamento", example = "3", required = true)
        private String formaPagamento;

        @NotBlank(message = "NumeroAgenciaCliente é obrigatório.")
        @ApiModelProperty(value = "Número da agência do cliente", example = "16", required = true)
        private String numeroAgenciaCliente;

        @NotBlank(message = "NumeroContaCorrenteCliente é obrigatório.")
        @ApiModelProperty(value = "Número da conta corrente do cliente", example = "200000", required = true)
        private String numeroContaCorrenteCliente;

        @NotBlank(message = "DigitoContaCorrenteCliente é obrigatório.")
        @ApiModelProperty(value = "Dígito da conta corrente do cliente", example = "6", required = true)
        private String digitoContaCorrenteCliente;

        @NotBlank(message = "TipoConta é obrigatório.")
        @ApiModelProperty(value = "Tipo da conta", example = "1", required = true)
        private String tipoConta;

        @NotBlank(message = "LinhaDigitavel é obrigatório.")
        @ApiModelProperty(value = "Linha digitável do boleto", example = "858900000018005004321333650716132634000024226661", required = true)
        private String linhaDigitavel;

        @NotBlank(message = "CodigoBarra é obrigatório.")
        @ApiModelProperty(value = "Código de barras", example = "83680000000106400110003768640221007004512043", required = true)
        private String codigoBarra;

        @NotBlank(message = "CodigoUnidadeRecolhedora é obrigatório.")
        @ApiModelProperty(value = "Código da unidade recolhedora", example = "16", required = true)
        private String codigoUnidadeRecolhedora;

        @NotBlank(message = "Valor é obrigatório.")
        @ApiModelProperty(value = "Valor da cobrança", example = "100,5", required = true)
        private String valor;
    }
}
