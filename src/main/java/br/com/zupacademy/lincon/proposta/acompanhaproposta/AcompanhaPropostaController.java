package br.com.zupacademy.lincon.proposta.acompanhaproposta;

import br.com.zupacademy.lincon.proposta.associacartao.PropostaRepository;
import br.com.zupacademy.lincon.proposta.exceptionhandlers.NegocioException;
import br.com.zupacademy.lincon.proposta.novaproposta.Proposta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/propostas")
public class AcompanhaPropostaController {

    @Autowired
    private PropostaRepository propostaRepository;

    @GetMapping("/{id}")
    public DetalhePropostaResponse findById(@PathVariable("id") Long id){
        Proposta proposta = propostaRepository.findById(id)
                .orElseThrow(() -> new NegocioException("Proposta não " +
                        "encontrada", HttpStatus.NOT_FOUND));
        return new DetalhePropostaResponse(proposta);
    }
}
