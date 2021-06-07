package br.com.zupacademy.lincon.proposta.sistemasexternos;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class IntegracoesController {

    private AtomicInteger conDocumentos = new AtomicInteger();

    public String avaliaDocumento(@RequestBody NovoDocumentoRequest request) {
        int contAtual = conDocumentos.getAndIncrement();
        if(contAtual % 2 != 0) {
            return "COM_RESTRICAO";
        }

        return "SEM_RESTRICAO";
    }

}
