package br.com.zupacademy.lincon.proposta.novaproposta;

import br.com.zupacademy.lincon.proposta.sistemasexternos.Integracoes;
import br.com.zupacademy.lincon.proposta.sistemasexternos.NovoDocumentoRequest;
import br.com.zupacademy.lincon.proposta.sistemasexternos.ResultadoSolicitacaoResponse;
import feign.RetryableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Service
@Validated
public class AvaliaProposta {
    @Autowired
    private Integracoes integracoes;

    public StatusAvaliacaoProposta executa(@NotNull @Validated Proposta proposta) throws RetryableException {

        ResultadoSolicitacaoResponse resultadoAvaliacao =
                integracoes.avalia(new NovoDocumentoRequest(proposta));
        return StatusAvaliacaoResponse
                .valueOf(resultadoAvaliacao.getResultadoAvaliacao()
                        .name()).getStatusAvaliacaoProposta();


//        return StatusAvaliacaoResponse.valueOf(resultadoAvaliacao).getStatusAvaliacaoProposta();
    }
}
