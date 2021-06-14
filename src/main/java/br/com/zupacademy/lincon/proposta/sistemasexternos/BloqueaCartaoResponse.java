package br.com.zupacademy.lincon.proposta.sistemasexternos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BloqueaCartaoResponse {
    @JsonProperty
    private String resultado;

    @Deprecated
    public BloqueaCartaoResponse() {
    }
}
