package br.com.bb.oauth.controller;

import lombok.Data;

@Data
public class PagamentoRequest {
    private double valorPagamento;
    private String codigoBarras48;
    private String codigoBarras44;
    private String codigoConvenio;
}
