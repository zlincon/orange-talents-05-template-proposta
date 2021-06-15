package br.com.zupacademy.lincon.proposta.associacartao;

import br.com.zupacademy.lincon.proposta.novaproposta.ExecutorTransacao;
import br.com.zupacademy.lincon.proposta.novaproposta.Proposta;
import br.com.zupacademy.lincon.proposta.novaproposta.StatusAvaliacaoProposta;
import br.com.zupacademy.lincon.proposta.sistemasexternos.IntegracoesCPF;
import br.com.zupacademy.lincon.proposta.sistemasexternos.IntegracoesCartoes;
import br.com.zupacademy.lincon.proposta.sistemasexternos.NovoCartaoResponse;
import br.com.zupacademy.lincon.proposta.sistemasexternos.NovoDocumentoRequest;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@Component
public class AssociaPropostaCartaoScheduler {

    private static final Logger log =
            LoggerFactory.getLogger(AssociaPropostaCartaoScheduler.class);
    @Autowired
    private IntegracoesCartoes integracoesCartoes;
    @Autowired
    private ExecutorTransacao executorTransacao;
    @Autowired
    private PropostaRepository propostaRepository;

    @Scheduled(fixedDelayString = "${periodicidade.associa-proposta-cartao}")
    @GetMapping("/executa-associacao-proposta")
    public void associa() {
        System.out.println("===========Scheduled==============");
        List<Proposta> propostas =
                propostaRepository.todasSemCartao(StatusAvaliacaoProposta.ELEGIVEL);
        log.info("Existem {} propostas para avaliar", propostas.size());

        for (Proposta proposta : propostas) {
            try{
                NovoCartaoResponse numero =
                        integracoesCartoes.buscaNumeroCartao(new NovoDocumentoRequest(proposta));
                proposta.associaCartao(numero.getId());
                executorTransacao.updateAndCommit(proposta);
                log.info("Proposta [{}] teve cartão associado", proposta.getId());
            }catch (FeignException e){
                continue;
            }
        }
    }

}
