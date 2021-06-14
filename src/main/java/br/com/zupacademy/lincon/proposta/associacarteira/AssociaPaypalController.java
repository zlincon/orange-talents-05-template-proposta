package br.com.zupacademy.lincon.proposta.associacarteira;

import br.com.zupacademy.lincon.proposta.associacartao.Cartao;
import br.com.zupacademy.lincon.proposta.criabiometria.CartaoRepository;
import br.com.zupacademy.lincon.proposta.exceptionhandlers.NegocioException;
import br.com.zupacademy.lincon.proposta.novaproposta.ExecutorTransacao;
import br.com.zupacademy.lincon.proposta.sistemasexternos.IntegracoesCartoes;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Map;

@RestController
@Validated
public class AssociaPaypalController {

    private static final Logger log =
            LoggerFactory.getLogger(AssociaPaypalController.class);
    protected ExecutorTransacao executorTransacao;
    private CartaoRepository cartaoRepository;
    private IntegracoesCartoes integracoesCartoes;

    @Autowired
    public AssociaPaypalController(CartaoRepository cartaoRepository, ExecutorTransacao executorTransacao, IntegracoesCartoes integracoesCartoes) {
        this.cartaoRepository = cartaoRepository;
        this.executorTransacao = executorTransacao;
        this.integracoesCartoes = integracoesCartoes;
    }

    @PostMapping("/api/cartoes/{id}/associa-paypal")
    public ResponseEntity<?> associa(@PathVariable("id") Long id,
                                     @Valid @RequestBody AssociaCarteiraRequest associaCarteiraRequest,
                                     UriComponentsBuilder componentsBuilder) {
        Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new NegocioException(
                "Cartão não encontrado. id: " + id, HttpStatus.NOT_FOUND));
        CarteiraPaypal carteiraPaypal =
                cartao.adicionaPaypal(associaCarteiraRequest.getEmail())
                        .orElseThrow(() -> new NegocioException("Cartão já " +
                                "associado a esta carteira Paypal",
                                HttpStatus.UNPROCESSABLE_ENTITY));

        log.debug("Associando cartão {} com paypal", id);
        try {
            integracoesCartoes.associaPaypal(cartao.getNumero(), Map.of("email",
                    associaCarteiraRequest.getEmail(), "carteira", "PAYPAL"));
            log.debug("Cartão {} associado com paypal", id);
            executorTransacao.updateAndCommit(cartao);

        } catch (FeignException e) {
            throw new NegocioException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        log.debug("Associação entre cartão {} e paypal feita no sistema",
                id);

        URI uri = componentsBuilder.path("/api/cartoes/{id}/carteiras/paypal" +
                "/{idPaypal" +
                "}").build(id, carteiraPaypal.getId());

        return ResponseEntity.created(uri).build();

    }
}
