package br.com.zupacademy.lincon.proposta.sistemasexternos;


import br.com.zupacademy.lincon.proposta.avisoviagem.NovoAvisoViagemRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(url = "${proposta.services.sistema-cartao.host}:${proposta" +
        ".services.sistema-cartao.port}", name =
        "integracoesCartoes")
public interface IntegracoesCartoes {

    @PostMapping(value = "/api/cartoes")
    NovoCartaoResponse buscaNumeroCartao(NovoDocumentoRequest request);

    @PostMapping("/api/cartoes/{id}/bloqueios")
    BloqueaCartaoResponse bloquearCartao(@PathVariable("id") String id,
                                         BloqueaCartaoRequest bloqueaCartaoRequest);

    @PostMapping("/api/cartoes/{id}/avisos")
    AvisoViagemResponse avisoViagem(@PathVariable("id") String id,
                                    NovoAvisoViagemRequest avisoViagemRequest);

    @PostMapping("/api/cartoes/{id}/carteiras")
    ResponseEntity<?> associaPaypal(@PathVariable("id") String numero,
                                           @RequestBody Map<String,
                                                   String> email);
}