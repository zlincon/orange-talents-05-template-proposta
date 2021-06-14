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
public class AssociaSamsungPaylController {

    private static final Logger log =
            LoggerFactory.getLogger(AssociaSamsungPaylController.class);
    protected ExecutorTransacao executorTransacao;
    private CartaoRepository cartaoRepository;
    private IntegracoesCartoes integracoesCartoes;

    @Autowired
    public AssociaSamsungPaylController(CartaoRepository cartaoRepository, ExecutorTransacao executorTransacao, IntegracoesCartoes integracoesCartoes) {
        this.cartaoRepository = cartaoRepository;
        this.executorTransacao = executorTransacao;
        this.integracoesCartoes = integracoesCartoes;
    }

    @PostMapping("/api/cartoes/{id}/associa-samsung-pay")
    public ResponseEntity<?> associa(@PathVariable("id") Long id,
                                     @Valid @RequestBody AssociaCarteiraRequest associaCarteiraRequest,
                                     UriComponentsBuilder componentsBuilder) {
        Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new NegocioException(
                "Cartão não encontrado. id: " + id, HttpStatus.NOT_FOUND));
        CarteiraSamsungPay carteiraSamsungPay =
                cartao.adicionaSamsungPay(associaCarteiraRequest.getEmail())
                        .orElseThrow(() -> new NegocioException("Cartão já " +
                                "associado a esta carteira Samsung Pay",
                                HttpStatus.UNPROCESSABLE_ENTITY));

        log.debug("Associando cartão {} com samsung pay", id);
        try {
            integracoesCartoes.associaCarteiraDigital(cartao.getNumero(), Map.of("email",
                    associaCarteiraRequest.getEmail(), "carteira", "SAMSUNG " +
                            "PAY"));
            log.debug("Cartão {} associado com samsung pay", id);
            executorTransacao.updateAndCommit(cartao);

        } catch (FeignException e) {
            throw new NegocioException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        log.debug("Associação entre cartão {} e samsung pay feita no sistema",
                id);

        URI uri = componentsBuilder.path("/api/cartoes/{id}/carteiras/samsung" +
                "-pay" +
                "/{idSamsungPay" +
                "}").build(id, carteiraSamsungPay.getId());

        return ResponseEntity.created(uri).build();

    }
}
