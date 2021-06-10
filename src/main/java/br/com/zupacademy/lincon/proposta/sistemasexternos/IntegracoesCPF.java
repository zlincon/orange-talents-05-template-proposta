package br.com.zupacademy.lincon.proposta.sistemasexternos;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(url = "${proposta.services.analise-financeira.host}:${proposta" +
        ".services.analise-financeira.port}", name =
        "integracoesCPF")
public interface IntegracoesCPF {

    @PostMapping(value = "/api/solicitacao")
    public ResultadoSolicitacaoResponse avalia(NovoDocumentoRequest request);
}