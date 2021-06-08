package br.com.zupacademy.lincon.proposta.novaproposta;

import br.com.zupacademy.lincon.proposta.exceptionhandlers.NegocioException;
import feign.FeignException;
import feign.RetryableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.util.Map;

@RestController
public class CriaNovaPropostaController {
    @Autowired
    private ExecutorTransacao executorTransacao;
    @Autowired
    private AvaliaProposta avaliaProposta;
    @Autowired
    private BloqueiaDocumentoDuplicatoValidator bloqueiaDocumentoDuplicatoValidator;

    @PostMapping(value = "/propostas")
    @Transactional
    public ResponseEntity<?> cria(
            @RequestBody @Valid NovaPropostaRequest request,
            UriComponentsBuilder builder) {

        if (!bloqueiaDocumentoDuplicatoValidator.estaValido(request)) {
            throw new NegocioException("Documento já existe em nossa base de " +
                    "dados",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }

        try {
            Proposta novaProposta = request.toModel();
            executorTransacao.saveAndCommit(novaProposta);
            StatusAvaliacaoProposta avaliacao =
                    null;
            try {
                avaliacao = avaliaProposta.executa(novaProposta);
            } catch (RetryableException e) {
                throw new NegocioException("Tente novamente mais tarde. - " +
                        "(Feign) Connection refused " +
                        "(Connection refused) executing " +
                        "POST http://localhost:9999/api/solicitacao",
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
            novaProposta.atualizaStatus(avaliacao);
            executorTransacao.updateAndCommit(novaProposta);
            URI enderecoConsulta =
                    builder.path("/propostas/{id}").build(novaProposta.getId());
            return ResponseEntity.created(enderecoConsulta).build();
        } catch (FeignException.FeignServerException e) {
            throw new NegocioException("(Feign) Error",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (FeignException.FeignClientException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(Map.of("message", "CPF com " +
                    "restrição"));
        }
    }
}
