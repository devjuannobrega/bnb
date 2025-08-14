package br.gov.bnb.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;


@Data
public class ReceiverDTO {

    @ApiModelProperty(value = "Código do canal. Deve ter exatamente 1 dígito.")
    @NotNull
    @Size(min = 1, max = 1, message = "CodigoCanal deve ter 1 dígito")
    private Integer codigoCanal;

    @ApiModelProperty(value = "Data de lançamento no formato dd/MM/yyyy")
    @NotNull
    private String dataLancamento;

    @ApiModelProperty(value = "Forma de pagamento: '1' para débito C/C ou '3' para dinheiro.")
    @NotNull
    private String formaPagamento;

    @ApiModelProperty(value = "Número do caixa.")
    @NotNull
    private Integer numeroCaixa;

    @ApiModelProperty(value = "Código de barras (44 caracteres). Exclusivo com linha digitável.")
    @Size(min = 44, max = 44, message = "CodigoBarra deve ter 44 caracteres")
    private String codigoBarra;

    @ApiModelProperty(value = "Linha digitável (48 caracteres). Exclusivo com código de barras.")
    @Size(min = 48, max = 48, message = "LinhaDigitavel deve ter 48 caracteres")
    private String linhaDigitavel;

    @ApiModelProperty(value = "Número da agência do cliente (obrigatório se formaPagamento = '1').")
    @NotNull
    private Integer numeroAgenciaCliente;

    @ApiModelProperty(value = "Número da conta corrente do cliente (obrigatório se formaPagamento = '1').")
    @NotNull
    private Integer numeroContaCorrenteCliente;

    @ApiModelProperty(value = "Dígito da conta corrente do cliente (obrigatório se formaPagamento = '1').")
    @NotNull
    private Integer digitoContaCorrenteCliente;

    @ApiModelProperty(value = "Tipo de conta. Fixo '1' se formaPagamento = '1'.")
    @NotNull
    private String tipoConta;

    @ApiModelProperty(value = "Código da unidade recolhedora.")
    @NotNull
    private Integer codigoUnidadeRecolhedora;

    @ApiModelProperty(value = "Valor opcional no formato numérico, usando vírgula ou ponto como separador decimal.")
    private String valor;

    @ApiModelProperty(value = "Usuário que realiza a operação.")
    @NotNull
    private String usuario;

    public boolean isCodigoBarraOuLinhaDigitavelValido() {
        boolean hasBarra = codigoBarra != null && !codigoBarra.isBlank();
        boolean hasLinha = linhaDigitavel != null && !linhaDigitavel.isBlank();
        return hasBarra ^ hasLinha; // XOR: apenas um deve ser informado
    }
}

