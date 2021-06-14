package br.com.zupacademy.lincon.proposta.sistemasexternos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BloqueaCartaoRequest {
    @JsonProperty
    private String sistemaResponsavel;

    @Deprecated
    public BloqueaCartaoRequest() {
    }

    public BloqueaCartaoRequest(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }
}
