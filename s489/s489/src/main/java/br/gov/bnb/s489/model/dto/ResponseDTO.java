package br.gov.bnb.s489.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Resposta do Orquestrador contendo os dados do XML e do usuário.")
public class ResponseDTO {

    @ApiModelProperty(value = "Dados do XML já convertidos em objeto", required = true)
    private String xml;

    @ApiModelProperty(value = "Usuário que realizou a operação", example = "CONTROLM", required = true)
    private String usuario;

    @Data
    @ApiModel(description = "Estrutura do ObjectData presente no XML")
    public static class XmlObjectData {

        @ApiModelProperty(value = "Indica se é um novo destino", example = "true")
        private boolean newTarget;

        @ApiModelProperty(value = "Lista de operações presentes no XML")
        private Operacao[] operacoes;

        @ApiModelProperty(value = "Dados do recolhimento")
        private Recolhimento recolhimento;

        @Data
        @ApiModel(description = "Operação definida no XML")
        public static class Operacao {
            @ApiModelProperty(value = "Descrição da operação", example = "Excluir")
            private String description;

            @ApiModelProperty(value = "Código da operação", example = "4")
            private String code;

            @ApiModelProperty(value = "Situação da operação", example = "A")
            private String situation;

            @ApiModelProperty(value = "OID da operação", example = "3")
            private String oid;
        }

        @Data
        @ApiModel(description = "Informações de recolhimento")
        public static class Recolhimento {
            @ApiModelProperty(value = "Código de barras", example = "82640000000105415350000020221505657520100104")
            private String codigoBarra;

            @ApiModelProperty(value = "Data do movimento", example = "04/08/2025")
            private String dataMovimento;

            @ApiModelProperty(value = "Valor do recolhimento", example = "10,54")
            private String valor;

            @ApiModelProperty(value = "Data de lançamento", example = "04/08/2025")
            private String dataLancamento;

            @ApiModelProperty(value = "Flag se foi processado", example = "N")
            private String processado;

            @ApiModelProperty(value = "Flag se foi prestado conta", example = "N")
            private String prestadoConta;

            @ApiModelProperty(value = "Data do pagamento", example = "04/08/2025")
            private String dataPagamento;

            @ApiModelProperty(value = "Dígito da conta corrente do cliente", example = "1")
            private String digitoContaCorrenteCliente;

            @ApiModelProperty(value = "Número da conta corrente do cliente", example = "3874")
            private String numeroContaCorrenteCliente;

            @ApiModelProperty(value = "Data de registro", example = "04/08/2025 11:13:04")
            private String dataRegistro;

            @ApiModelProperty(value = "Data de registro do pagamento", example = "04/08/2025 11:13:06")
            private String dataRegistroPagamento;

            @ApiModelProperty(value = "Rajada", example = "N")
            private String rajada;

            @ApiModelProperty(value = "Código identificador", example = "82640000000105415350000020221505657520100104")
            private String codigoIdentificador;

            @ApiModelProperty(value = "Data de prestação de conta", example = "05/08/2025")
            private String dataPrestacaoConta;

            @ApiModelProperty(value = "OID do recolhimento", example = "")
            private String oid;

            @ApiModelProperty(value = "Número da agência do cliente", example = "0016")
            private String numeroAgenciaCliente;

            @ApiModelProperty(value = "Informações do canal")
            private Canal canal;

            @Data
            @ApiModel(description = "Informações do canal")
            public static class Canal {
                @ApiModelProperty(value = "Descrição do canal", example = "Nordeste Eletronico")
                private String descricao;

                @ApiModelProperty(value = "Data inicial de validade", example = "18/03/2009")
                private String dataIniValidade;

                @ApiModelProperty(value = "Usuário que atualizou", example = "C015973")
                private String usuarioAtualizador;

                @ApiModelProperty(value = "Data da última atualização", example = "26/03/2021 12:00:45")
                private String dataUltAtualizacao;

                @ApiModelProperty(value = "Rotina atualizadora", example = "ucCadastroCanal")
                private String rotinaAtualizadora;

                @ApiModelProperty(value = "Código do canal", example = "5")
                private String codigo;

                @ApiModelProperty(value = "Usuário logado", example = "CONTROLM")
                private String usuarioLogado;

                @ApiModelProperty(value = "Exige informação de autenticação", example = "N")
                private String exigeInformacaoAutenticacao;

                @ApiModelProperty(value = "Débito automático", example = "N")
                private String debitoAutomatico;

                @ApiModelProperty(value = "Auto atendimento", example = "S")
                private String autoAtendimento;

                @ApiModelProperty(value = "Valida feriado local", example = "S")
                private String validaFeriadoLocal;

                @ApiModelProperty(value = "OID do canal", example = "5")
                private String oid;
            }
        }
    }
}
