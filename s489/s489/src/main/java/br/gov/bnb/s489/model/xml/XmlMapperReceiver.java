package br.gov.bnb.s489.model.xml;

import jakarta.xml.bind.annotation.*;
import lombok.Data;
import java.util.List;

@Data
@XmlRootElement(name = "SYSMSG")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlMapperReceiver {

    @XmlElement(name = "ObjectData")
    private ObjectData objectData;

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "operacoes", "recolhimento" })
    public static class ObjectData {

        @XmlAttribute(name = "NewTarget")
        private String newTarget;

        @XmlElement(name = "ngOperation")
        private List<NgOperation> operacoes;

        @XmlElement(name = "Recolhimento")
        private Recolhimento recolhimento;
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class NgOperation {
        @XmlAttribute(name = "Description")
        private String description;

        @XmlAttribute(name = "Code")
        private String code;

        @XmlAttribute(name = "Situation")
        private String situation;

        @XmlAttribute(name = "OID")
        private String oid;
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Recolhimento {

        @XmlAttribute(name = "CodigoBarra")
        private String codigoBarra;

        @XmlAttribute(name = "DataMovimento")
        private String dataMovimento;

        @XmlAttribute(name = "Valor")
        private String valor;

        @XmlAttribute(name = "DataLancamento")
        private String dataLancamento;

        @XmlAttribute(name = "Processado")
        private String processado;

        @XmlAttribute(name = "PrestadoConta")
        private String prestadoConta;

        @XmlAttribute(name = "DataPagamento")
        private String dataPagamento;

        @XmlAttribute(name = "DigitoContaCorrenteCliente")
        private String digitoContaCorrenteCliente;

        @XmlAttribute(name = "NumeroContaCorrenteCliente")
        private String numeroContaCorrenteCliente;

        @XmlAttribute(name = "DataRegistro")
        private String dataRegistro;

        @XmlAttribute(name = "DataRegistroPagamento")
        private String dataRegistroPagamento;

        @XmlAttribute(name = "Rajada")
        private String rajada;

        @XmlAttribute(name = "CodigoIdentificador")
        private String codigoIdentificador;

        @XmlAttribute(name = "DataPrestacaoConta")
        private String dataPrestacaoConta;

        @XmlAttribute(name = "OID")
        private String oid;

        @XmlAttribute(name = "NumeroAgenciaCliente")
        private String numeroAgenciaCliente;

        @XmlElement(name = "Canal")
        private Canal canal;
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Canal {

        @XmlAttribute(name = "Descricao")
        private String descricao;

        @XmlAttribute(name = "Datainivalidade")
        private String dataIniValidade;

        @XmlAttribute(name = "usuarioatualizador")
        private String usuarioAtualizador;

        @XmlAttribute(name = "dataultatualizacao")
        private String dataUltAtualizacao;

        @XmlAttribute(name = "rotinaatualizadora")
        private String rotinaAtualizadora;

        @XmlAttribute(name = "Codigo")
        private String codigo;

        @XmlAttribute(name = "usuariologado")
        private String usuarioLogado;

        @XmlAttribute(name = "ExigeInformacaoAutenticacao")
        private String exigeInformacaoAutenticacao;

        @XmlAttribute(name = "DebitoAutomatico")
        private String debitoAutomatico;

        @XmlAttribute(name = "AutoAtendimento")
        private String autoAtendimento;

        @XmlAttribute(name = "ValidaFeriadoLocal")
        private String validaFeriadoLocal;

        @XmlAttribute(name = "OID")
        private String oid;
    }
}
