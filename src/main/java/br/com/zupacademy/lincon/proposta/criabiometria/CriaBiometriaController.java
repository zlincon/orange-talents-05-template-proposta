package br.com.zupacademy.lincon.proposta.criabiometria;

import br.com.zupacademy.lincon.proposta.associacartao.Cartao;
import br.com.zupacademy.lincon.proposta.exceptionhandlers.NegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class CriaBiometriaController {

    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private BiometriaRepository biometriaRepository;

    @PostMapping("/api/cartoes/{id}/biometrias")
    @Transactional
    public ResponseEntity cria(@PathVariable("id") Long id,
                               @RequestBody @Valid NovaBiometriaRequest request,
                               UriComponentsBuilder uriComponentsBuilder) {
        Cartao cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new NegocioException("Cartão não " +
                        "encontrado com id: "+id,
                        HttpStatus.NOT_FOUND));

//        Biometria biometria = cartao.adicionaBiometria(request.getDigital());

        Biometria biometria = new Biometria(cartao, request.getDigital());

        biometriaRepository.save(biometria);

        URI uri =
                uriComponentsBuilder.path("/api/cartoes/{idCartao}/biometrias" +
                        "/{id}").buildAndExpand(id,biometria.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
