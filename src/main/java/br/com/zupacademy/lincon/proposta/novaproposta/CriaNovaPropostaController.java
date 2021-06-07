package br.com.zupacademy.lincon.proposta.novaproposta;

import br.com.zupacademy.lincon.proposta.exceptionhandlers.NegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

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

        if(!bloqueiaDocumentoDuplicatoValidator.estaValido(request)){
            throw new NegocioException("Documento já existe em nossa base de " +
                    "dados",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Proposta novaProposta = request.toModel();
        executorTransacao.saveAndCommit(novaProposta);

        StatusAvaliacaoProposta avaliacao = avaliaProposta.executa(novaProposta);
        novaProposta.atualizaStatus(avaliacao);
        executorTransacao.updateAndCommit(novaProposta);

        URI enderecoConsulta =
                builder.path("/propostas/{id}").build(novaProposta.getId());
        return ResponseEntity.created(enderecoConsulta).build();
    }

}
