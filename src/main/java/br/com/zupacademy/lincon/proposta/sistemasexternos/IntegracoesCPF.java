package br.com.zupacademy.lincon.proposta.sistemasexternos;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(url = "${API_CPF}", name = "integracoesCPF")
public interface IntegracoesCPF {

    @PostMapping(value = "/api/solicitacao")
    public ResultadoSolicitacaoResponse avalia(NovoDocumentoRequest request);
}