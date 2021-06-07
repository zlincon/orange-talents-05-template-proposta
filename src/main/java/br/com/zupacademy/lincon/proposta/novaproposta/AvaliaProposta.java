package br.com.zupacademy.lincon.proposta.novaproposta;

import br.com.zupacademy.lincon.proposta.sistemasexternos.Integracoes;
import br.com.zupacademy.lincon.proposta.sistemasexternos.NovoDocumentoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Service
@Validated
public class AvaliaProposta {
    @Autowired
    private Integracoes integracoes;

    public StatusAvaliacaoProposta executa(@NotNull @Validated Proposta proposta) {
        String resultadoAvaliacao =
                integracoes.avalia(new NovoDocumentoRequest(proposta));

        return StatusAvaliacaoResponse.valueOf(resultadoAvaliacao).getStatusAvaliacaoProposta();
    }
}
