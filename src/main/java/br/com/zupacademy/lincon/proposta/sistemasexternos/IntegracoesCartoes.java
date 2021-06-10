package br.com.zupacademy.lincon.proposta.sistemasexternos;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "${API_CARTAO}", name = "integracoesCartoes")
public interface IntegracoesCartoes {

    @PostMapping(value = "/api/cartoes")
    NovoCartaoResponse buscaNumeroCartao(NovoDocumentoRequest request);
}