package br.com.zupacademy.lincon.proposta.compartilhado;

import br.com.zupacademy.lincon.proposta.novaproposta.AvaliaProposta;
import br.com.zupacademy.lincon.proposta.novaproposta.Proposta;
import feign.RetryableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("api/solicitacoes")
public class ApiSolicitacaoHealthIndicator implements HealthIndicator, HealthContributor {

    @Autowired
    private AvaliaProposta avaliaProposta;

    @Override
    public Health health() {
        try {
            avaliaProposta.executa(new Proposta("","","", BigDecimal.ONE,"043.090" +
                    ".893-80"));
        }catch (RetryableException e){
            return Health.down().withDetail("error",
                    "Not possible connect with this aplication " +
                            "{https://localhost:9999/api/solicitacao}").build();
        }

        return Health.up().build();
    }
}
