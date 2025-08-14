package br.gov.bnb.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Resultado do Wrapper a ser exibido no body da resposta")
public class ResponseDTO {

    @ApiModelProperty(value = "Código HTTP da resposta", example = "200")
    private int httpStatus = 200;

    @ApiModelProperty(value = "Indica se a requisição foi bem sucedida", example = "true")
    private boolean success = true;

    @ApiModelProperty(value = "Objeto contendo o XML e usuário")
    private Result result;

    @ApiModelProperty(value = "Mensagem de erro, caso exista")
    private String error;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Result {
        @ApiModelProperty(value = "XML de retorno", example = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><SYSMSG>...</SYSMSG>")
        private String xml;

        @ApiModelProperty(value = "Usuário responsável pela operação", example = "CONTROLM")
        private String usuario;
    }
}
