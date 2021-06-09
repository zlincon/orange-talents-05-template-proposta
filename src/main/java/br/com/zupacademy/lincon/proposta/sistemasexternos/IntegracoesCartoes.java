package br.com.zupacademy.lincon.proposta.sistemasexternos;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "localhost:8888/api", name = "integracoesCartoes")
public interface IntegracoesCartoes {

    @PostMapping(value = "/cartoes")
    NovoCartaoResponse buscaNumeroCartao(NovoDocumentoRequest request);
}