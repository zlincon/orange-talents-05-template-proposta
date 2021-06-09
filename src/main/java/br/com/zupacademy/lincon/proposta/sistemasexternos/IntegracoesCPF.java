package br.com.zupacademy.lincon.proposta.sistemasexternos;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(url = "localhost:9999/api", name = "integracoesCPF")
public interface IntegracoesCPF {

    @PostMapping(value = "/solicitacao")
    public ResultadoSolicitacaoResponse avalia(NovoDocumentoRequest request);
}