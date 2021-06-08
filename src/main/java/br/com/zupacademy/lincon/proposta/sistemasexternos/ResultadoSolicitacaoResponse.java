package br.com.zupacademy.lincon.proposta.sistemasexternos;

import br.com.zupacademy.lincon.proposta.novaproposta.StatusAvaliacaoResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultadoSolicitacaoResponse {
    @JsonProperty("resultadoSolicitacao")
    private StatusAvaliacaoResponse resultadoAvaliacao;

    @Deprecated
    public ResultadoSolicitacaoResponse() {
    }

    public StatusAvaliacaoResponse getResultadoAvaliacao() {
        return resultadoAvaliacao;
    }
}
