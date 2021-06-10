package br.com.zupacademy.lincon.proposta.compartilhado;

import br.com.zupacademy.lincon.proposta.novaproposta.AvaliaProposta;
import br.com.zupacademy.lincon.proposta.novaproposta.Proposta;
import br.com.zupacademy.lincon.proposta.sistemasexternos.IntegracoesCartoes;
import br.com.zupacademy.lincon.proposta.sistemasexternos.NovoDocumentoRequest;
import feign.RetryableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.Socket;

@Component("Api - Gerar numeração cartão")
public class ApiGerarCartaoHealthIndicator implements HealthIndicator{

    @Value("${proposta.services.sistema-cartao.host}:${proposta" +
            ".services" +
            ".sistema-cartao.port}")
    private String url;

    @Override
    public Health health() {
        try (Socket socket =
                     new Socket(new java.net.URL(url+"/api/cartoes").getHost(),
                             8888)) {
        } catch (Exception e) {
            return Health.down().withDetail("error", e.getMessage()).build();
        }
        return Health.up().build();
    }
}
